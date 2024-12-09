package np.com.lashman.learnit.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import np.com.lashman.learnit.data.repository.SubjectRepositoryImpl
import np.com.lashman.learnit.data.repository.TaskRepositoryImpl
import np.com.lashman.learnit.domain.repository.SubjectRepository
import np.com.lashman.learnit.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Marks this module for dependency injection in the SingletonComponent
abstract class RepositoryModule {

    @Singleton  // Specifies that the provided repository should have a single instance
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl  // Binds the SubjectRepositoryImpl to the SubjectRepository interface
    ): SubjectRepository

    @Singleton  // Specifies that the provided repository should have a single instance
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl  // Binds the TaskRepositoryImpl to the TaskRepository interface
    ): TaskRepository
}
