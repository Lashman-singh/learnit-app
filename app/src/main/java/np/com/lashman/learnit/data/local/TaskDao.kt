package np.com.lashman.learnit.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import np.com.lashman.learnit.domain.model.Task
import kotlinx.coroutines.flow.Flow

// Data Access Object (DAO) for performing database operations on Task.
@Dao
interface TaskDao {

    // Inserts or updates a task in the database.
    @Upsert
    suspend fun upsertTask(task: Task)

    // Deletes a task by its ID.
    @Query("DELETE FROM Task WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Int)

    // Deletes all tasks associated with a specific subject ID.
    @Query("DELETE FROM Task WHERE taskSubjectId = :subjectId")
    suspend fun deleteTasksBySubjectId(subjectId: Int)

    // Retrieves a task by its ID.
    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    // Retrieves all tasks for a specific subject as a Flow.
    @Query("SELECT * FROM Task WHERE taskSubjectId = :subjectId")
    fun getTasksForSubject(subjectId: Int): Flow<List<Task>>

    // Retrieves all tasks as a Flow.
    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>
}
