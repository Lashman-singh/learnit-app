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
}