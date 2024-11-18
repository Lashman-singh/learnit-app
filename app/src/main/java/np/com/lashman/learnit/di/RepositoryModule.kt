package np.com.lashman.learnit.di

import np.com.lashman.learnit.data.repository.SessionRepositoryImpl
import np.com.lashman.learnit.data.repository.SubjectRepositoryImpl
import np.com.lashman.learnit.data.repository.TaskRepositoryImpl
import np.com.lashman.learnit.domain.repository.SessionRepository
import np.com.lashman.learnit.domain.repository.SubjectRepository
import np.com.lashman.learnit.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}