package dev.nikdekur.classcharts.web.modal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PupilDataModal(
    val id: Int,
    val name: String,

    @SerialName("first_name")
    val firstName: String,

    @SerialName("last_name")
    val lastName: String,

    @SerialName("avatar_url")
    val avatarUrl: String,

    @SerialName("display_behaviour")
    val displayBehaviour: Boolean,

    @SerialName("display_parent_behaviour")
    val displayParentBehaviour: Boolean,

    @SerialName("display_homework")
    val displayHomework: Boolean,

    @SerialName("display_rewards")
    val displayRewards: Boolean,

    @SerialName("display_detentions")
    val displayDetentions: Boolean,

    @SerialName("display_report_cards")
    val displayReportCards: Boolean,

    @SerialName("display_classes")
    val displayClasses: Boolean,

    @SerialName("display_announcements")
    val displayAnnouncements: Boolean,

    @SerialName("display_academic_reports")
    val displayAcademicReports: Boolean,

    @SerialName("display_attendance")
    val displayAttendance: Boolean,

    @SerialName("display_attendance_type")
    val displayAttendanceType: String,

    @SerialName("display_attendance_percentage")
    val displayAttendancePercentage: Boolean,

    @SerialName("display_activity")
    val displayActivity: Boolean,

    @SerialName("display_mental_health")
    val displayMentalHealth: Boolean,

    @SerialName("display_mental_health_no_tracker")
    val displayMentalHealthNoTracker: Boolean,

    @SerialName("display_timetable")
    val displayTimetable: Boolean,

    @SerialName("is_disabled")
    val isDisabled: Boolean,

    @SerialName("display_two_way_communications")
    val displayTwoWayCommunications: Boolean,

    @SerialName("display_absences")
    val displayAbsences: Boolean,

    @SerialName("can_upload_attachments")
    val canUploadAttachments: Boolean?,

    @SerialName("display_event_badges")
    val displayEventBadges: Boolean,

    @SerialName("display_avatars")
    val displayAvatars: Boolean,

    @SerialName("display_concern_submission")
    val displayConcernSubmission: Boolean,

    @SerialName("display_custom_fields")
    val displayCustomFields: Boolean,

    @SerialName("pupil_concerns_help_text")
    val pupilConcernsHelpText: String,

    @SerialName("allow_pupils_add_timetable_notes")
    val allowPupilsAddTimetableNotes: Boolean,

    @SerialName("detention_alias_plural_uc")
    val detentionAliasPluralUc: String,
)