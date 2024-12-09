package np.com.lashman.learnit.presentation.task

import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.util.Priority

// Data class to represent the state of a task
data class TaskState(
    // Title of the task (empty string by default)
    val title: String = "",

    // Description of the task (empty string by default)
    val description: String = "",

    // Due date for the task represented in milliseconds, null if not set
    val dueDate: Long? = null,

    // Flag to indicate if the task is marked as complete or not
    val isTaskComplete: Boolean = false,

    // Priority of the task (default is LOW)
    val priority: Priority = Priority.LOW,

    // The subject related to the task (null if not set)
    val relatedToSubject: String? = null,

    // List of subjects available to associate with the task
    val subjects: List<Subject> = emptyList(),

    // ID of the selected subject (null if not set)
    val subjectId: Int? = null,

    // ID of the current task (null if not editing an existing task)
    val currentTaskId: Int? = null
)
