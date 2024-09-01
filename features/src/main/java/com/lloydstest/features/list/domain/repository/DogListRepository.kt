package com.lloydstest.features.list.domain.repository

import com.lloydstest.features.list.data.model.response.DogListDto

interface DogListRepository {
    suspend fun getDogList(breed: String): DogListDto
}