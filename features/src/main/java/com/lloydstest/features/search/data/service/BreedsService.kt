package com.lloydstest.features.search.data.service

import com.lloydstest.features.search.data.model.response.BreedListDto
import retrofit2.http.GET

interface BreedsService {
    @GET("api/breeds/list/all")
    suspend fun getDogsBreed(): BreedListDto
}