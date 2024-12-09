package np.com.lashman.learnit.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import np.com.lashman.learnit.data.local.AppDatabase
import np.com.lashman.learnit.data.local.SubjectDao
import np.com.lashman.learnit.data.local.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Marks this module for dependency injection in the SingletonComponent
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "learnit.db" // Database name
            )
            .addMigrations(MIGRATION_1_2) // Adds migration logic for version 1 to 2
            .build()  // Builds and returns the database instance
    }

    @Provides
    @Singleton
    fun provideSubjectDao(database: AppDatabase): SubjectDao {
        return database.subjectDao()  // Provides the SubjectDao from the database
    }

    @Provides
    @Singleton
    fun provideTaskDaoDao(database: AppDatabase): TaskDao {
        return database.taskDao()  // Provides the TaskDao from the database
    }

    // Migration from version 1 to version 2
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Drop the 'Session' table if it exists (since you've deleted it)
            // database.execSQL("DROP TABLE IF EXISTS session")
        }
    }
}
