package com.lloydstest.features.search.domain.use_case

import com.lloydstest.features.search.domain.model.BreedList
import com.lloydstest.network.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FilterBreedsUseCase {
    operator fun invoke(breeds: List<String>, query: String): Flow<Resource<BreedList>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(BreedList(breedList = breeds.filter { it.contains(query) })))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}