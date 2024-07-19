package com.kotlin.dependecyinjection

import android.content.Context
import androidx.room.Room
import com.kotlin.unittest.roomdb.ArtDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,ArtDataBase::class.java)
            .allowMainThreadQueries()
            .build()
}