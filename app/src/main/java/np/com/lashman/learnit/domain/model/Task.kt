package np.com.lashman.learnit.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity  // Marks this class as a Room entity (table in the database)
data class Task(
    val title: String,  // The title of the task
    val description: String,  // A description of the task
    val dueDate: Long,  // The due date of the task represented as a timestamp (milliseconds)
    val priority: Int,  // The priority level of the task (e.g., 1 for high priority)
    val relatedToSubject: String,  // The subject related to the task
    val isComplete: Boolean,  // A flag indicating if the task is complete
    val taskSubjectId: Int,  // The subject ID that the task is related to
    @PrimaryKey(autoGenerate = true)  // Marks this field as the primary key with auto-increment
    val taskId: Int? = null  // Task ID, which is auto-generated
)
