package com.example.firebasepractice.di

import androidx.lifecycle.ViewModel
import com.example.firebasepractice.firReDb.repository.RealtimeRepository
import com.example.firebasepractice.firReDb.repository.RealtimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repo: RealtimeRepositoryImpl): RealtimeRepository
}