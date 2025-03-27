package com.panwar2001.myapplication.di

import androidx.lifecycle.LiveData
import com.panwar2001.myapplication.DataRepoInterface
import com.panwar2001.myapplication.DataRepository
import com.panwar2001.myapplication.NetworkObserver
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

    @Singleton
    @Binds
    abstract fun bindNetworkObserver(networkObserver: NetworkObserver): LiveData<Boolean>
}
