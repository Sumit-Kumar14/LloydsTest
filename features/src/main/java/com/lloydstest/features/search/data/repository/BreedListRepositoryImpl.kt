package com.lloydstest.features.search.data.repository

import com.lloydstest.features.search.data.model.response.BreedListDto
import com.lloydstest.features.search.data.service.BreedsService
import com.lloydstest.features.search.domain.repository.BreedListRepository
import com.lloydstest.network.core.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreedListRepositoryImpl: BreedListRepository {
    private val service = NetworkClient.getRetrofit().create(BreedsService::class.java)

    override suspend fun getBreeds(): BreedListDto {
        return withContext(Dispatchers.IO) {
            service.getDogsBreed()
        }
    }
}