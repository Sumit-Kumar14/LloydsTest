package com.lloydstest.features.list.domain.use_case

import com.lloydstest.features.list.data.model.response.toDogsDomain
import com.lloydstest.features.list.data.repository.DogListRepositoryImpl
import com.lloydstest.features.list.domain.model.DogList
import com.lloydstest.features.list.domain.repository.DogListRepository
import com.lloydstest.network.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchDogsByBreedUseCase {
    private val repository: DogListRepository = DogListRepositoryImpl()

    operator fun invoke(breed: String): Flow<Resource<DogList>> = flow {
        try {
            emit(Resource.Loading())
            val dogs = repository.getDogList(breed = breed).toDogsDomain()
            emit(Resource.Success(dogs))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}