package np.com.lashman.learnit.presentation.dashboard

import androidx.compose.ui.graphics.Color
import np.com.lashman.learnit.domain.model.Task

sealed class DashboardEvent {
    data object SaveSubject : DashboardEvent()
    data class OnTaskIsCompleteChange(val task: Task): DashboardEvent()
    data class OnSubjectCardColorChange(val colors: List<Color>): DashboardEvent()
    data class OnSubjectNameChange(val name: String): DashboardEvent()
    data class OnGoalStudyHoursChange(val hours: String): DashboardEvent()
}
