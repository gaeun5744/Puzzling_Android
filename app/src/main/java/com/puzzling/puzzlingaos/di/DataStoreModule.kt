package com.puzzling.puzzlingaos.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.puzzling.puzzlingaos.data.datasource.local.TokenDataSource
import com.puzzling.puzzlingaos.data.datasource.local.UserDataSource
import com.puzzling.puzzlingaos.data.entity.Token
import com.puzzling.puzzlingaos.data.entity.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val USER_PREFERENCES_NAME = "user_preferences"
    private const val DATA_STORE_FILE_NAME = "user_prefs.pb"

    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext appContext: Context,
        tokenDataSource: TokenDataSource,
    ): DataStore<Token> {
        return DataStoreFactory.create(
            serializer = tokenDataSource,
            produceFile = {
                appContext.dataStoreFile(DATA_STORE_FILE_NAME)
            },
        )
    }

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext appContext: Context,
        userDataSource: UserDataSource,
    ): DataStore<User> {
        return DataStoreFactory.create(
            serializer = userDataSource,
            produceFile = {
                appContext.dataStoreFile(DATA_STORE_FILE_NAME)
            },
        )
    }
}