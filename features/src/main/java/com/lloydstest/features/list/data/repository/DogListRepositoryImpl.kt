package com.lloydstest.features.list.data.repository

import com.lloydstest.features.list.data.model.response.DogListDto
import com.lloydstest.features.list.data.service.DogsService
import com.lloydstest.features.list.domain.repository.DogListRepository
import com.lloydstest.network.core.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogListRepositoryImpl: DogListRepository {
    private val service = NetworkClient.getRetrofit().create(DogsService::class.java)

    override suspend fun getDogList(breed: String): DogListDto {
        return withContext(Dispatchers.IO) {
            service.getDogsByBreed(breed = breed)
        }
    }
}