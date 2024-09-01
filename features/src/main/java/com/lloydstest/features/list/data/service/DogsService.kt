package com.lloydstest.features.list.data.service

import com.lloydstest.features.list.data.model.response.DogListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsService {
    @GET("api/breed/{breed}/images")
    suspend fun getDogsByBreed(@Path("breed", encoded = true) breed: String): DogListDto
}