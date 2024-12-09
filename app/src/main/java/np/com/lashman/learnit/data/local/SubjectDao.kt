package np.com.lashman.learnit.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import np.com.lashman.learnit.domain.model.Subject
import kotlinx.coroutines.flow.Flow

// Data Access Object (DAO) for performing database operations on Subject.
@Dao
interface SubjectDao {

    // Inserts or updates a subject in the database.
    @Upsert
    suspend fun upsertSubject(subject: Subject)

    // Retrieves the total count of subjects as a Flow.
    @Query("SELECT COUNT(*) FROM SUBJECT")
    fun getTotalSubjectCount(): Flow<Int>

    // Retrieves the total sum of goal hours as a Flow.
    @Query("SELECT SUM(goalHours) FROM SUBJECT")
    fun getTotalGoalHours(): Flow<Float>

    // Retrieves a subject by its ID.
    @Query("SELECT * FROM Subject WHERE subjectId = :subjectId")
    suspend fun getSubjectById(subjectId: Int): Subject?

    // Deletes a subject by its ID.
    @Query("DELETE FROM Subject WHERE subjectId = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    // Retrieves all subjects as a Flow.
    @Query("SELECT * FROM Subject")
    fun getAllSubjects(): Flow<List<Subject>>
}
