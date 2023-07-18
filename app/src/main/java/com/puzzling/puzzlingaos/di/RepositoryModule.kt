package com.puzzling.puzzlingaos.di

import com.puzzling.puzzlingaos.data.repository.MyBoardRepositoryImpl
import com.puzzling.puzzlingaos.domain.repository.MyBoardRepository
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
    abstract fun providesMyBoardRepository(repoImpl: MyBoardRepositoryImpl): MyBoardRepository
}