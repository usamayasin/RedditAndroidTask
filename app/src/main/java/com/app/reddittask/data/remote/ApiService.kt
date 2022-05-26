package com.app.reddittask.data.remote

import com.app.reddittask.data.model.CharactersResponse
import com.app.reddittask.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(AppConstants.NETWORK.CHARACTER_END_POINT)
    suspend fun getCharacters(
        @Query(AppConstants.NETWORK.PAGE) page: Int
    ): Response<CharactersResponse>


}