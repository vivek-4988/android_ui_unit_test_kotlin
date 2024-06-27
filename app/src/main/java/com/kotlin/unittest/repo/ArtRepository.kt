package com.kotlin.unittest.repo

import androidx.lifecycle.LiveData
import com.kotlin.unittest.model.ImageResponse
import com.kotlin.unittest.roomdb.Art
import com.kotlin.util.Resource

interface ArtRepositoryInterface {


    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>


}