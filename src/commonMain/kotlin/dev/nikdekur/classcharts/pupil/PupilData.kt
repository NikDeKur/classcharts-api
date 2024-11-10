package dev.nikdekur.classcharts.pupil

import dev.nikdekur.ndkore.`interface`.Unique

interface PupilData : Unique<Int> {
    
    val name: String

    val firstName: String
    val lastName: String
    
    val avatarUrl: String

    val isDisabled: Boolean

    val displayBehaviour: Boolean
    val displayParentBehaviour: Boolean
    val displayHomework: Boolean
    val displayRewards: Boolean
    val displayDetentions: Boolean
    val displayReportCards: Boolean
    val displayClasses: Boolean
    val displayAnnouncements: Boolean
    val displayAcademicReports: Boolean
    val displayAttendance: Boolean
    val displayAttendanceType: String
    val displayAttendancePercentage: Boolean
    val displayActivity: Boolean
    val displayMentalHealth: Boolean
    val displayMentalHealthNoTracker: Boolean
    val displayTimetable: Boolean
    val displayTwoWayCommunications: Boolean
    val displayAbsences: Boolean
    val displayEventBadges: Boolean
    val displayAvatars: Boolean
    val displayConcernSubmission: Boolean
    val displayCustomFields: Boolean

    val canUploadAttachments: Boolean?

    val pupilConcernsHelpText: String

    val allowPupilsAddTimetableNotes: Boolean

    val detentionAliasPluralUc: String
}