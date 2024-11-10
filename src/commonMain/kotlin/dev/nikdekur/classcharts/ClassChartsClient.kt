package dev.nikdekur.classcharts

import dev.nikdekur.classcharts.pupil.PupilData
import dev.nikdekur.ornament.Application

interface ClassChartsClient : Application {
    val pupilData: PupilData
}