package np.com.lashman.learnit.data.repository

import kotlinx.coroutines.flow.Flow
import np.com.lashman.learnit.data.local.SubjectDao
import np.com.lashman.learnit.data.local.TaskDao
import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.domain.repository.SubjectRepository
import javax.inject.Inject

// Implementation of SubjectRepository using DAOs for database operations.
class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao, // DAO for Subject operations.
    private val taskDao: TaskDao, // DAO for Task operations.
) : SubjectRepository {

    // Inserts or updates a subject in the database.
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(subject)
    }

    // Returns the total number of subjects as a Flow.
    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDao.getTotalSubjectCount()
    }

    // Returns the total sum of goal hours as a Flow.
    override fun getTotalGoalHours(): Flow<Float> {
        return subjectDao.getTotalGoalHours()
    }

    // Deletes a subject and its associated tasks by subject ID.
    override suspend fun deleteSubject(subjectId: Int) {
        taskDao.deleteTasksBySubjectId(subjectId)
        subjectDao.deleteSubject(subjectId)
    }

    // Retrieves a subject by its ID.
    override suspend fun getSubjectById(subjectId: Int): Subject? {
        return subjectDao.getSubjectById(subjectId)
    }

    // Retrieves all subjects as a Flow.
    override fun getAllSubjects(): Flow<List<Subject>> {
        return subjectDao.getAllSubjects()
    }
}
