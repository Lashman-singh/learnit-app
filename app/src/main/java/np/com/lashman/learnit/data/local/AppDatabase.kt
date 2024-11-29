package np.com.lashman.learnit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.domain.model.Task

@Database(
    entities = [Subject::class, Task::class],
    version = 2
)
@TypeConverters(ColorListConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun taskDao(): TaskDao
}

