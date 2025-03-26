package com.plcoding.coilimagecachingguide.di

import com.panwar2001.myapplication.DataRepoInterface
import com.panwar2001.myapplication.DataRepository
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