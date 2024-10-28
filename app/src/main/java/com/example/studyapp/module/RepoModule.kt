package com.example.studyapp.module

import com.example.studyapp.data.repo.SessionRepoImp
import com.example.studyapp.data.repo.SubjectRepoImp
import com.example.studyapp.data.repo.TaskRepoImp
import com.example.studyapp.domain.repo.SessionRepo
import com.example.studyapp.domain.repo.SubjectRepo
import com.example.studyapp.domain.repo.TaskRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Singleton
    @Binds
    abstract fun binSubjectRepo(
        impl: SubjectRepoImp
    ): SubjectRepo

    @Singleton
    @Binds
    abstract fun bindTaskRepo(
        impl: TaskRepoImp
    ): TaskRepo

    @Singleton
    @Binds
    abstract fun bindSessionRepo(
        impl: SessionRepoImp
    ): SessionRepo

}