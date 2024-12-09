package np.com.lashman.learnit.presentation.dashboard

import androidx.compose.ui.graphics.Color
import np.com.lashman.learnit.domain.model.Subject

data class DashboardState(
    // The total number of subjects the user has added
    val totalSubjectCount: Int = 0,

    // The total number of hours studied by the user
    val totalStudiedHours: Float = 0f,

    // The total number of study hours the user aims to complete (goal hours)
    val totalGoalStudyHours: Float = 0f,

    // A list of Subject objects representing the subjects the user has added
    val subjects: List<Subject> = emptyList(),

    // The name of the subject that the user is currently adding or editing
    val subjectName: String = "",

    // The goal study hours for the subject being added or edited
    val goalStudyHours: String = "",

    // A list of colors to be used for the subject cards, selected randomly from available colors
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random()
)
