package np.com.lashman.learnit.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import np.com.lashman.learnit.presentation.theme.gradient1
import np.com.lashman.learnit.presentation.theme.gradient2
import np.com.lashman.learnit.presentation.theme.gradient3
import np.com.lashman.learnit.presentation.theme.gradient4
import np.com.lashman.learnit.presentation.theme.gradient5

@Entity  // Marks this class as a Room entity (table in the database)
data class Subject(
    val name: String,  // The name of the subject
    val goalHours: Float,  // The goal hours set for the subject
    val colors: List<Int>,  // List of color resources used for the subject
    @PrimaryKey(autoGenerate = true)  // Marks this field as the primary key with auto-increment
    val subjectId: Int? = null  // Subject ID, which is auto-generated
) {
    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)  // Predefined gradient colors for subject cards
    }
}
