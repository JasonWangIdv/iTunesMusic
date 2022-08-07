package com.cinnox.itunes.repository.retrofit

import com.cinnox.itunes.repository.bean.SearchBean
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {

    @GET("/search?media=music")
    suspend fun searchMusicByArtisName(@Query("term") artisName: String): Response<SearchBean>

}