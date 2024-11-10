@file:OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.licenser)
    alias(libs.plugins.kotlinSerialization)
    id("maven-publish")
}

group = "dev.nikdekur"
version = "1.0.0"

val authorId: String by project
val authorName: String by project

kotlin {


    val javaVersion = JavaVersion.VERSION_21
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = javaVersion.toString()
        }

        compilerOptions {
            freeCompilerArgs.addAll("-Xno-param-assertions", "-Xno-call-assertions")
        }
    }


    js {
        moduleName = project.name
        browser()
        nodejs()
    }

    wasmJs {
        moduleName = project.name + "Wasm"
        browser()
        nodejs()
    }



    // Dependencies
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.properties)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlin.logging)

            implementation(libs.ndkore)
            implementation(libs.ornament)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.test)
        }

        listOf(jvmMain, jvmTest).forEach {
            it.dependencies {
                implementation(libs.slf4j.simple)
                implementation(libs.ktor.client.cio)
            }
        }

        listOf(jsMain, jsTest, wasmJsMain, wasmJsTest).forEach {
            it.dependencies {
                implementation(libs.ktor.client.js)
            }
        }
    }
}

val repoUsernameProp = "NDK_REPO_USERNAME"
val repoPasswordProp = "NDK_REPO_PASSWORD"
val repoUsername: String? = System.getenv(repoUsernameProp)
val repoPassword: String? = System.getenv(repoPasswordProp)

if (repoUsername.isNullOrBlank() || repoPassword.isNullOrBlank()) {
    throw GradleException("Environment variables $repoUsernameProp and $repoPasswordProp must be set.")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            pom {
                developers {
                    developer {
                        id.set(authorId)
                        name.set(authorName)
                    }
                }
            }

            from(components["kotlin"])
        }
    }

    repositories {
        maven {
            name = "ndk-repo"
            url = uri("https://repo.nikdekur.tech/releases")
            credentials {
                username = repoUsername
                password = repoPassword
            }
        }

        mavenLocal()
    }
}

license {
    header(project.file("HEADER"))
    properties {
        set("year", "2024-present")
        set("name", authorName)
    }

    ignoreFailures = true
}