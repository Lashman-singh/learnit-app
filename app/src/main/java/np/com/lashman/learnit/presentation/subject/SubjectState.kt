package np.com.lashman.learnit.presentation.subject

import androidx.compose.ui.graphics.Color
import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.domain.model.Task

data class SubjectState(
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val studiedHours: Float = 0f,
    val progress: Float = 0f,
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList()
)
