package dev.nikdekur.classcharts.pupil

import dev.nikdekur.classcharts.web.modal.PupilDataModal
import kotlinx.serialization.Serializable

@Serializable
data class ModalPupilData(
    val modal: PupilDataModal
) : PupilData {

    override val id = modal.id

    override val name = modal.name
    override val firstName = modal.firstName
    override val lastName = modal.lastName
    override val avatarUrl = modal.avatarUrl

    override val isDisabled = modal.isDisabled

    override val displayBehaviour = modal.displayBehaviour
    override val displayParentBehaviour = modal.displayParentBehaviour
    override val displayHomework = modal.displayHomework
    override val displayRewards = modal.displayRewards
    override val displayDetentions = modal.displayDetentions
    override val displayReportCards = modal.displayReportCards
    override val displayClasses = modal.displayClasses
    override val displayAnnouncements = modal.displayAnnouncements
    override val displayAcademicReports = modal.displayAcademicReports
    override val displayAttendance = modal.displayAttendance
    override val displayAttendanceType = modal.displayAttendanceType
    override val displayAttendancePercentage = modal.displayAttendancePercentage
    override val displayActivity = modal.displayActivity
    override val displayMentalHealth = modal.displayMentalHealth
    override val displayMentalHealthNoTracker = modal.displayMentalHealthNoTracker
    override val displayTimetable = modal.displayTimetable
    override val displayTwoWayCommunications = modal.displayTwoWayCommunications
    override val displayAbsences = modal.displayAbsences
    override val displayEventBadges = modal.displayEventBadges
    override val displayAvatars = modal.displayAvatars
    override val displayConcernSubmission = modal.displayConcernSubmission
    override val displayCustomFields = modal.displayCustomFields

    override val canUploadAttachments = modal.canUploadAttachments

    override val pupilConcernsHelpText = modal.pupilConcernsHelpText

    override val allowPupilsAddTimetableNotes = modal.allowPupilsAddTimetableNotes

    override val detentionAliasPluralUc = modal.detentionAliasPluralUc
}