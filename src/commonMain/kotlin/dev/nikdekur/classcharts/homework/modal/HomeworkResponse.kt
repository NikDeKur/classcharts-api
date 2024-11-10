package dev.nikdekur.classcharts.homework.modal

import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeworkResponse(
    var data: List<Data>,
    var meta: Meta
) : ClassChartsResponse() {


    @Serializable
    data class Data(
        val lesson: String,

        @SerialName("subject")
        val subject: String?,

        @SerialName("teacher")
        val teacher: String,

        @SerialName("homework_type")
        val homeworkType: String,

        @SerialName("id")
        val id: Int,

        @SerialName("title")
        val title: String,

        @SerialName("meta_title")
        val metaTitle: String,

        @SerialName("description")
        val description: String,

        @SerialName("issue_date")
        val issueDate: String,

        @SerialName("due_date")
        val dueDate: String,

        @SerialName("completion_time_unit")
        val completionTimeUnit: String,

        @SerialName("completion_time_value")
        val completionTimeValue: String,

        @SerialName("publish_time")
        val publishTime: String,

        @SerialName("status")
        val status: Status,

        @SerialName("validated_links")
        val validatedLinks: List<ValidatedLink>,

        @SerialName("validated_attachments")
        val validatedAttachments: List<ValidatedAttachment>
    )

    @Serializable
    data class ValidatedLink(
        val link: String,

        @SerialName("validated_link")
        val validatedLink: String
    )



    @Serializable
    data class Status(
        @SerialName("id")
        val id: Int,
        @SerialName("state")
        val state: String?,
        @SerialName("mark")
        val mark: String?,
        @SerialName("mark_relative")
        val markRelative: Int,
        @SerialName("ticked")
        var ticked: String,
        @SerialName("allow_attachments")
        val allowAttachments: Boolean,
        @SerialName("allow_marking_completed")
        val allowMarkingCompleted: Boolean,
        @SerialName("first_seen_date")
        val firstSeenDate: String?,
        @SerialName("last_seen_date")
        val lastSeenDate: String?,
        @SerialName("attachments")
        val attachments: List<Attachment>,
        @SerialName("has_feedback")
        val hasFeedback: Boolean
    )



    @Serializable
    data class Attachment(
        @SerialName("id")
        val id: Int,
        @SerialName("file_name")
        val fileName: String,
        @SerialName("file")
        val file: String,
        @SerialName("validated_file")
        val validatedFile: String,
        @SerialName("teacher_note")
        val teacherNote: String,
        @SerialName("teacher_homework_attachment")
        val teacherHomeworkAttachment: List<TeacherAttachment>,
        @SerialName("can_delete")
        val canDelete: Boolean
    )

    @Serializable
    data class TeacherAttachment(
        @SerialName("id")
        val id: Int,
        @SerialName("file_name")
        val fileName: String,
        @SerialName("file")
        val file: String,
        @SerialName("validated_file")
        val validatedFile: String,
    )

    @Serializable
    data class ValidatedAttachment(
        @SerialName("id")
        val id: Int,


        @SerialName("filename")
        val filename: String,


        @SerialName("file")
        val file: String,


        @SerialName("validated_file")
        val validatedFile: String,


        @SerialName("scan_status")
        val scanStatus: String,


        @SerialName("scan_status_message")
        val scanStatusMessage: String,
    )


    @Serializable
    data class Meta(
        @SerialName("start_date")
        val startDate: String,
        @SerialName("end_date")
        val endDate: String,
        @SerialName("display_type")
        val displayType: String,
        @SerialName("max_files_allowed")
        val maxFilesAllowed: Int,
        @SerialName("allowed_file_types")
        val allowedFileTypes: List<String>,
        @SerialName("this_week_due_count")
        val thisWeekDueCount: Int,
        @SerialName("this_week_outstanding_count")
        val thisWeekOutstandingCount: Int,
        @SerialName("this_week_completed_count")
        val thisWeekCompletedCount: Int,
        @SerialName("allow_attachments")
        val allowAttachments: Boolean,
        @SerialName("display_marks")
        val displayMarks: Boolean
    )
}