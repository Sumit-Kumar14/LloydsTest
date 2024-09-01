package com.lloydstest.features.search.domain.repository

import com.lloydstest.features.search.data.model.response.BreedListDto
import kotlinx.coroutines.flow.Flow

interface BreedListRepository {
    suspend fun getBreeds(): BreedListDto
}