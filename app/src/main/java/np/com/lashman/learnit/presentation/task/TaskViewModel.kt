package np.com.lashman.learnit.presentation.task

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import np.com.lashman.learnit.domain.model.Task
import np.com.lashman.learnit.domain.repository.SubjectRepository
import np.com.lashman.learnit.domain.repository.TaskRepository
import np.com.lashman.learnit.presentation.navArgs
import np.com.lashman.learnit.util.Priority
import np.com.lashman.learnit.util.SnackbarEvent
import java.time.Instant
import javax.inject.Inject

// ViewModel class responsible for managing task-related data and interactions
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val subjectRepository: SubjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Extracting navigation arguments using SavedStateHandle
    private val navArgs: TaskScreenNavArgs = savedStateHandle.navArgs()

    // State flow representing the task's state
    private val _state = MutableStateFlow(TaskState())

    // Combining state and subjects to keep the UI state updated with the latest subjects
    val state = combine(
        _state,
        subjectRepository.getAllSubjects()
    ) { state, subjects ->
        state.copy(subjects = subjects) // Updating the state with available subjects
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = TaskState()
    )

    // Shared flow to emit Snackbar events for displaying messages
    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()

    // Initialize by fetching the task and subject data
    init {
        fetchTask()
        fetchSubject()
    }

    // Handling events from the UI (such as changes in task details)
    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnTitleChange -> {
                _state.update {
                    it.copy(title = event.title) // Update title
                }
            }

            is TaskEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(description = event.description) // Update description
                }
            }

            is TaskEvent.OnDateChange -> {
                _state.update {
                    it.copy(dueDate = event.millis) // Update due date
                }
            }

            is TaskEvent.OnPriorityChange -> {
                _state.update {
                    it.copy(priority = event.priority) // Update priority
                }
            }

            TaskEvent.OnIsCompleteChange -> {
                _state.update {
                    it.copy(isTaskComplete = !_state.value.isTaskComplete) // Toggle task completion status
                }
            }

            is TaskEvent.OnRelatedSubjectSelect -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.subject.name, // Set the subject's name
                        subjectId = event.subject.subjectId // Set the subject's ID
                    )
                }
            }

            TaskEvent.SaveTask -> saveTask() // Trigger save task function
            TaskEvent.DeleteTask -> deleteTask() // Trigger delete task function
        }
    }

    // Deletes the task and handles success or error
    private fun deleteTask() {
        viewModelScope.launch {
            try {
                val currentTaskId = state.value.currentTaskId
                if (currentTaskId != null) {
                    withContext(Dispatchers.IO) {
                        taskRepository.deleteTask(taskId = currentTaskId) // Delete the task
                    }
                    // Show success message after deletion
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "Task deleted successfully")
                    )
                    _snackbarEventFlow.emit(SnackbarEvent.NavigateUp) // Navigate back
                } else {
                    // Show error if no task is found to delete
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "No Task to delete")
                    )
                }
            } catch (e: Exception) {
                // Show error message in case of an exception
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Couldn't delete task. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    // Saves the task and handles success or error
    private fun saveTask() {
        viewModelScope.launch {
            val state = _state.value
            // Check if subject is selected, if not show error message
            if (state.subjectId == null || state.relatedToSubject == null) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Please select subject related to the task"
                    )
                )
                return@launch
            }
            try {
                // Upsert (insert or update) the task into the repository
                taskRepository.upsertTask(
                    task = Task(
                        title = state.title,
                        description = state.description,
                        dueDate = state.dueDate ?: Instant.now().toEpochMilli(),
                        relatedToSubject = state.relatedToSubject,
                        priority = state.priority.value,
                        isComplete = state.isTaskComplete,
                        taskSubjectId = state.subjectId,
                        taskId = state.currentTaskId
                    )
                )
                // Show success message after saving the task
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(message = "Task Saved Successfully")
                )
                _snackbarEventFlow.emit(SnackbarEvent.NavigateUp) // Navigate back
            } catch (e: Exception) {
                // Show error message in case of an exception
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Couldn't save task. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    // Fetches task data by its ID (if available in navigation arguments)
    private fun fetchTask() {
        viewModelScope.launch {
            navArgs.taskId?.let { id ->
                taskRepository.getTaskById(id)?.let { task ->
                    _state.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            isTaskComplete = task.isComplete,
                            relatedToSubject = task.relatedToSubject,
                            priority = Priority.fromInt(task.priority),
                            subjectId = task.taskSubjectId,
                            currentTaskId = task.taskId
                        )
                    }
                }
            }
        }
    }

    // Fetches subject data by its ID (if available in navigation arguments)
    private fun fetchSubject() {
        viewModelScope.launch {
            navArgs.subjectId?.let { id ->
                subjectRepository.getSubjectById(id)?.let { subject ->
                    _state.update {
                        it.copy(
                            subjectId = subject.subjectId,
                            relatedToSubject = subject.name
                        )
                    }
                }
            }
        }
    }

}
