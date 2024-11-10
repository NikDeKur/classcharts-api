@file:OptIn(ExperimentalSerializationApi::class)

package dev.nikdekur.classcharts.web

import dev.nikdekur.classcharts.ClassChartsClient
import dev.nikdekur.classcharts.ClassChartsService
import dev.nikdekur.classcharts.ConnectData
import dev.nikdekur.classcharts.exception.LoginFailedException
import dev.nikdekur.classcharts.exception.PingFailedException
import dev.nikdekur.classcharts.pupil.ModalPupilData
import dev.nikdekur.classcharts.pupil.PupilData
import dev.nikdekur.classcharts.web.modal.*
import dev.nikdekur.ndkore.ext.printStackAndThrow
import dev.nikdekur.ndkore.ext.toBoolean
import dev.nikdekur.ndkore.scheduler.CoroutineScheduler
import dev.nikdekur.ndkore.scheduler.Scheduler
import dev.nikdekur.ndkore.scheduler.runTaskTimer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.reflect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.serializer
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.seconds
import io.ktor.client.statement.HttpResponse as KtorHttpResponse
import io.ktor.http.ContentType as KtorContentType
import io.ktor.http.HttpMethod as KtorHttpMethod


class KtorWebService(
    override val app: ClassChartsClient,
    val data: ConnectData
) : ClassChartsService(), WebService {


    lateinit var json: Json
    lateinit var properties: Properties
    lateinit var client: HttpClient
    lateinit var scheduler: Scheduler

    var pingPeriod = 190.seconds

    val sessionId: String?
        get() = data.sessionId

    var studentId: Int? = null

    fun setSessionId(sessionId: String) {
        data.setSessionId(sessionId)
    }

    val loginPath = path<LoginRequest, LoginResponse>(
        method = HttpMethod.POST,
        contentType = ContentType.Application.FormUrlEncoded,
        path = "login",
        requireAuth = false
    )

    val pingPath = path<PingRequest, PingResponse>(
        method = HttpMethod.POST,
        contentType = ContentType.Application.FormUrlEncoded,
        path = "ping",
        requireAuth = true
    )

    var pupilDataOrNull: PupilData? = null

    override val pupilData: PupilData
        get() = pupilDataOrNull ?: error("Pupil data not loaded!")


    override suspend fun onEnable() {
        json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        client = HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
        }

        properties = Properties(json.serializersModule)

        login()

        scheduler = CoroutineScheduler.fromSupervisor(Dispatchers.Default)

        scheduler.runTaskTimer(pingPeriod) {
            println("Pinging...")
            ping(resetSession = false)
            println("Pinged!")
        }
    }

    override suspend fun onDisable() {
        scheduler.shutdown()
        client.close()
    }

    fun formPath(relative: String, parameters: Map<String, Any>): String {
        var base = "https://www.classcharts.com/apiv2student/$relative"
        (studentId?.let {
            parameters + ("studentId" to it)
        } ?: parameters).forEach { (key, value) ->
            base = base.replace("{$key}", value.toString())
        }

        return base.also {
            println(studentId)
            println("After form: $it")
        }
    }


    override suspend fun <T> request(
        path: Path<T>,
        body: T,
        parameters: Map<String, Any>
    ): HttpResponse {
        val cType = path.contentType

        val bodyType = path.bodyType
        val bodyClassType = bodyType.classifier as KClass<*>
        val bodyTypeInfo = TypeInfo(
            bodyType.classifier as KClass<*>,
            bodyType
        )

        val returnType = path.returnType
        val returnTypeInfo = TypeInfo(
            returnType.classifier as KClass<*>,
            returnType
        )

        val shared: HttpRequestBuilder.() -> Unit = {
            method = KtorHttpMethod.parse(path.method.name)

            if (path.requireAuth) {
                header(HttpHeaders.Authorization, "Basic $sessionId")
            }
        }

        val path = formPath(path.path, parameters)

        val response = try {
            if (cType == ContentType.Application.FormUrlEncoded) {
                val serializer = properties.serializersModule.serializer(bodyClassType, emptyList(), false)
                val paramsMap = properties.encodeToMap(serializer, body)
                val params = parameters {
                    paramsMap.forEach { (key, value) ->
                        append(key, value.toString())
                    }
                }

                client.submitForm(path, params) {
                    shared()
                }

            } else {
                client.request(path) {
                    contentType(KtorContentType(cType.type, cType.subtype))
                    setBody(body, bodyTypeInfo)
                    shared()

                }
            }
        } catch (e: Throwable) {
            println(e)
            e.printStackAndThrow()
        }

        return HttpResponseWrapper(response, returnTypeInfo)
    }


    suspend fun login() {
        val sessionId = sessionId
        if (sessionId != null) {
            setSessionId(sessionId)
            ping(resetSession = true)
            return
        }

        val studentCode = data.studentCode ?: error("Student code not set!")
        val dob = data.dateOfBirth ?: error("Date of birth not set!")

        val request = LoginRequest(studentCode, dob.toString(), true, "")

        val response = request(loginPath, request)

        val body = response.getBody()
        if (body is ClassChartsResponse.Error) {
            val error = body.error
            val problem =
                if (error.contains("pupil code"))
                    LoginFailedException.Problem.INVALID_STUDENT_CODE

                else if (error.contains("date of birth"))
                    LoginFailedException.Problem.INVALID_DATE_OF_BIRTH

                else
                    LoginFailedException.Problem.UNKNOWN

            throw LoginFailedException(problem, body.error)
        }

        val success = body as LoginResponse

        pupilDataOrNull = ModalPupilData(success.data)

        val newSessionId = success.meta.sessionId ?: error("Student Id wasn't provided during login!")
        setSessionId(newSessionId)
        studentId = pupilData.id
    }


    suspend fun ping(resetSession: Boolean) {
        val request = PingRequest(includeData = resetSession)
        val response = request(pingPath, request)

        val body = response.getBody()
        if (body is ClassChartsResponse.Error)
            throw PingFailedException(body.error, resetSession)

        val success = body as PingResponse

        pupilDataOrNull = ModalPupilData(success.data.user)

        if (resetSession) {
            val newSessionId = body.meta.sessionId
                ?: error("Student Id wasn't provided during ping with reset!")
            setSessionId(newSessionId)
        }
    }


    inner class HttpResponseWrapper(
        val original: KtorHttpResponse,
        val returnType: TypeInfo
    ) : HttpResponse {
        override val statusCode by original.status::value
        override suspend fun getBody(): ClassChartsResponse {
            val jsonDataStr = original.body<String>()
            println(jsonDataStr)
            val jsonData = json.parseToJsonElement(jsonDataStr)
            val success = (
                jsonData.jsonObject["success"]?.jsonPrimitive?.intOrNull ?: 1
            ).toBoolean()

            val type =
                if (success) returnType
                else typeInfo<ClassChartsResponse.Error>()

            return getBody(type)
        }

        override suspend fun <T> getBody(type: TypeInfo): T {
            return original.body(type)
        }


        override fun toString(): String {
            return "HttpResponseWrapper(original=$original, returnType=$returnType)"
        }
    }
}