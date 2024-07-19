package com.kotlin.unittest.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.unittest.model.ImageResponse
import com.kotlin.unittest.roomdb.Art
import com.kotlin.util.Resource
import org.junit.Assert.*

class FakeArtRepository : ArtRepositoryInterface{

    private val arts = mutableListOf<Art>()
    private var artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
       return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }

}