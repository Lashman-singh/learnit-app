package np.com.lashman.learnit.data.repository

import np.com.lashman.learnit.data.local.TaskDao
import np.com.lashman.learnit.domain.model.Task
import np.com.lashman.learnit.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Implementation of TaskRepository using TaskDao for database operations.
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao // DAO for Task-related operations.
) : TaskRepository {

    // Inserts or updates a task in the database.
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    // Deletes a task by its ID.
    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    // Retrieves a task by its ID.
    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    // Retrieves upcoming tasks for a specific subject, filters incomplete tasks, and sorts them.
    override fun getUpcomingTasksForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTasksForSubject(subjectId)
            .map { tasks -> tasks.filter { it.isComplete.not() } }
            .map { tasks -> sortTasks(tasks) }
    }

    // Retrieves completed tasks for a specific subject, filters complete tasks, and sorts them.
    override fun getCompletedTasksForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTasksForSubject(subjectId)
            .map { tasks -> tasks.filter { it.isComplete } }
            .map { tasks -> sortTasks(tasks) }
    }

    // Retrieves all upcoming tasks, filters incomplete tasks, and sorts them.
    override fun getAllUpcomingTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { tasks -> tasks.filter { it.isComplete.not() } }
            .map { tasks -> sortTasks(tasks) }
    }

    // Sorts tasks by due date (ascending) and priority (descending).
    private fun sortTasks(tasks: List<Task>): List<Task> {
        return tasks.sortedWith(compareBy<Task> { it.dueDate }.thenByDescending { it.priority })
    }
}