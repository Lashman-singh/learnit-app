package np.com.lashman.learnit.presentation.task

import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.util.Priority

// Sealed class for Task events
sealed class TaskEvent {

    // Event to change the title of the task
    data class OnTitleChange(val title: String) : TaskEvent()

    // Event to change the description of the task
    data class OnDescriptionChange(val description: String) : TaskEvent()

    // Event to change the date of the task, passed as milliseconds
    data class OnDateChange(val millis: Long?) : TaskEvent()

    // Event to change the priority of the task
    data class OnPriorityChange(val priority: Priority) : TaskEvent()

    // Event to select the subject related to the task
    data class OnRelatedSubjectSelect(val subject: Subject) : TaskEvent()

    // Event to change the completion status of the task
    data object OnIsCompleteChange : TaskEvent()

    // Event to save the task
    data object SaveTask : TaskEvent()

    // Event to delete the task
    data object DeleteTask : TaskEvent()
}
