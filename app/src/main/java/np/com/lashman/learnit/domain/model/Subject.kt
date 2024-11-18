package np.com.lashman.learnit.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import np.com.lashman.learnit.presentation.theme.gradient1
import np.com.lashman.learnit.presentation.theme.gradient2
import np.com.lashman.learnit.presentation.theme.gradient3
import np.com.lashman.learnit.presentation.theme.gradient4
import np.com.lashman.learnit.presentation.theme.gradient5

@Entity
data class Subject(
    val name: String,
    val goalHours: Float,
    val colors: List<Int>,
    @PrimaryKey(autoGenerate = true)
    val subjectId: Int? = null
) {
    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
