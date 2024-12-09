package np.com.lashman.learnit.presentation.dashboard

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.domain.model.Task
import np.com.lashman.learnit.domain.repository.SubjectRepository
import np.com.lashman.learnit.domain.repository.TaskRepository
import np.com.lashman.learnit.util.SnackbarEvent
import javax.inject.Inject

// ViewModel for managing Dashboard screen's state and events
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    // Mutable state to hold the dashboard data
    private val _state = MutableStateFlow(DashboardState())

    // Public state combining subject count, goal study hours, and subjects data
    val state = combine(
        _state,
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHours(),
        subjectRepository.getAllSubjects()
    ) { state, subjectCount, goalHours, subjects ->
        state.copy(
            totalSubjectCount = subjectCount,
            totalGoalStudyHours = goalHours,
            subjects = subjects
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = DashboardState()
    )

    // State for holding upcoming tasks
    val tasks: StateFlow<List<Task>> = taskRepository.getAllUpcomingTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    // Flow for managing Snackbar events (messages)
    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()

    // Function to handle dashboard events
    fun onEvent(event: DashboardEvent) {
        when (event) {
            // Event for changing the subject name
            is DashboardEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }

            // Event for changing the goal study hours
            is DashboardEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }

            // Event for changing the subject card colors
            is DashboardEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColors = event.colors)
                }
            }

            // Event to save a subject
            DashboardEvent.SaveSubject -> saveSubject()

            // Event for updating the task completion status
            is DashboardEvent.OnTaskIsCompleteChange -> {
                updateTask(event.task)
            }
        }
    }

    // Function to update a task's completion status in the repository
    private fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                // Upsert the task with updated completion status
                taskRepository.upsertTask(
                    task = task.copy(isComplete = !task.isComplete)
                )
                // Show a Snackbar message after successful task update
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(message = "Saved in completed tasks.")
                )
            } catch (e: Exception) {
                // Show a Snackbar message if task update fails
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        "Couldn't update task. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    // Function to save a new subject in the repository
    private fun saveSubject() {
        viewModelScope.launch {
            try {
                // Create a new Subject object and upsert it
                subjectRepository.upsertSubject(
                    subject = Subject(
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        colors = state.value.subjectCardColors.map { it.toArgb() }
                    )
                )
                // Reset the state after saving the subject
                _state.update {
                    it.copy(
                        subjectName = "",
                        goalStudyHours = "",
                        subjectCardColors = Subject.subjectCardColors.random()
                    )
                }
                // Show a Snackbar message confirming the subject save
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(message = "Subject saved successfully")
                )
            } catch (e: Exception) {
                // Show a Snackbar message if subject save fails
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Couldn't save subject. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
}
