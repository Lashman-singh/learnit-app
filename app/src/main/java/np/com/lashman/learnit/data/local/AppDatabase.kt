package np.com.lashman.learnit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.domain.model.Task

// Defines the Room database with Subject and Task entities.
@Database(
    entities = [Subject::class, Task::class],
    version = 2 // Specifies the database version for migration.
)
@TypeConverters(ColorListConverter::class) // Handles custom type conversion for the database.
abstract class AppDatabase : RoomDatabase() {

    // Returns DAO for Subject-related operations.
    abstract fun subjectDao(): SubjectDao

    // Returns DAO for Task-related operations.
    abstract fun taskDao(): TaskDao
}
