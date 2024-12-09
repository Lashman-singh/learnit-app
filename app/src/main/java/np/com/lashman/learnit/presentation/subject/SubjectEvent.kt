package np.com.lashman.learnit.presentation.subject

import androidx.compose.ui.graphics.Color
import np.com.lashman.learnit.domain.model.Task

// Sealed class for Subject events
sealed class SubjectEvent {

    // Event to update the subject
    data object UpdateSubject : SubjectEvent()

    // Event to delete the subject
    data object DeleteSubject : SubjectEvent()

    // Event to update the subject's progress
    data object UpdateProgress : SubjectEvent()

    // Event to change the completion status of a task
    data class OnTaskIsCompleteChange(val task: Task): SubjectEvent()

    // Event to change the subject card's color
    data class OnSubjectCardColorChange(val color: List<Color>): SubjectEvent()

    // Event to change the subject's name
    data class OnSubjectNameChange(val name: String): SubjectEvent()

    // Event to change the goal study hours for the subject
    data class OnGoalStudyHoursChange(val hours: String): SubjectEvent()
}
