package np.com.lashman.learnit.presentation.subject

import androidx.compose.ui.graphics.Color
import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.domain.model.Task

// Data class representing the state of a subject
data class SubjectState(
    // ID of the current subject, nullable if no subject is selected
    val currentSubjectId: Int? = null,

    // Name of the subject
    val subjectName: String = "",

    // Goal study hours for the subject
    val goalStudyHours: String = "",

    // List of colors for the subject card, randomly chosen from the subject's predefined colors
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),

    // Total hours studied for the subject
    val studiedHours: Float = 0f,

    // Progress made in the subject, typically represented as a percentage (0f to 1f)
    val progress: Float = 0f,

    // List of upcoming tasks for the subject
    val upcomingTasks: List<Task> = emptyList(),

    // List of completed tasks for the subject
    val completedTasks: List<Task> = emptyList()
)
