package com.lloydstest.features.search.domain.use_case

import com.lloydstest.features.search.data.model.response.toBreedDomain
import com.lloydstest.features.search.data.repository.BreedListRepositoryImpl
import com.lloydstest.features.search.domain.model.BreedList
import com.lloydstest.features.search.domain.repository.BreedListRepository
import com.lloydstest.network.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchBreedsUseCase {
    private val repository: BreedListRepository = BreedListRepositoryImpl()

    operator fun invoke(): Flow<Resource<BreedList>> = flow {
        try {
            emit(Resource.Loading())
            val breeds = repository.getBreeds().toBreedDomain()
            emit(Resource.Success(breeds))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}