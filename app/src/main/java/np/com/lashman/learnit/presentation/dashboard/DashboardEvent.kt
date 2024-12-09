package np.com.lashman.learnit.presentation.dashboard

import androidx.compose.ui.graphics.Color
import np.com.lashman.learnit.domain.model.Task

// Sealed class representing various events in the Dashboard screen
sealed class DashboardEvent {

    // Event to save a subject (e.g., when the user confirms the subject)
    data object SaveSubject : DashboardEvent()

    // Event when the completion status of a task changes
    data class OnTaskIsCompleteChange(val task: Task): DashboardEvent()

    // Event to change the color of a subject card, passing a list of colors
    data class OnSubjectCardColorChange(val colors: List<Color>): DashboardEvent()

    // Event to change the name of a subject
    data class OnSubjectNameChange(val name: String): DashboardEvent()

    // Event to change the goal study hours
    data class OnGoalStudyHoursChange(val hours: String): DashboardEvent()
}
