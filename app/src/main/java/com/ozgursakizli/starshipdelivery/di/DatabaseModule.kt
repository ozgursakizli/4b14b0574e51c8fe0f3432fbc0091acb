package com.ozgursakizli.starshipdelivery.di

import android.content.Context
import androidx.room.Room
import com.ozgursakizli.starshipdelivery.database.AppDatabase
import com.ozgursakizli.starshipdelivery.database.DatabaseConstants
import com.ozgursakizli.starshipdelivery.database.spaceship.SpaceshipDao
import com.ozgursakizli.starshipdelivery.database.stations.StationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DatabaseConstants.APP_DATABASE
        ).build()
    }

    @Provides
    fun provideSpaceshipDao(database: AppDatabase): SpaceshipDao {
        return database.spaceshipDao()
    }

    @Provides
    fun provideStationsDao(database: AppDatabase): StationsDao {
        return database.stationsDao()
    }

}