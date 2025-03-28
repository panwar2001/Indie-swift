package com.panwar2001.myapplication.di

import com.panwar2001.myapplication.core.DataRepoInterface
import com.panwar2001.myapplication.core.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class  RepoModule {
    @Singleton
    @Binds
    abstract fun bindAppRepo(repository: DataRepository): DataRepoInterface
}
