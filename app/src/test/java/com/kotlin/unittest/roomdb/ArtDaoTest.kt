package com.kotlin.unittest.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kotlin.unittest.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database : ArtDataBase


    private lateinit var dataBase: ArtDataBase
    private lateinit var dao: ArtDao

    @Before
    fun setUp() {
        /*dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArtDataBase::class.java
        ).allowMainThreadQueries().build()*/
        hiltRule.inject()

        dao = dataBase.artDao()
    }

    @Test
    fun insertArtTesting()  = runTest {
        val exampleArt = Art("William James", "Da Vinci", 1703, "test.com", 1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(exampleArt)
    }

    @Test
    fun deleteArtTesting()= runTest {
        val exampleArt = Art("John John", "Da Vinci", 1703, "test.com", 1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(exampleArt)
    }

    @After
    fun tearDown() {
        dataBase.close()
    }


}