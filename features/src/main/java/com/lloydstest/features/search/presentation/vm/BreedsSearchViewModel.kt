package com.lloydstest.features.search.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydstest.features.search.domain.use_case.FetchBreedsUseCase
import com.lloydstest.features.search.domain.use_case.FilterBreedsUseCase
import com.lloydstest.features.search.presentation.state.BreedListFilterUiState
import com.lloydstest.features.search.presentation.state.BreedListUiState
import com.lloydstest.network.core.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BreedsSearchViewModel : ViewModel() {

    private val breedsUseCase: FetchBreedsUseCase = FetchBreedsUseCase()
    private val filterBreedsUseCase: FilterBreedsUseCase = FilterBreedsUseCase()

    private val _fetchState = MutableStateFlow<BreedListUiState>(BreedListUiState.Loading)
    val fetchState = _fetchState

    private val _filterState = MutableStateFlow<BreedListFilterUiState>(BreedListFilterUiState.Loading)
    val filterState = _filterState

    fun fetchBreeds() {
        breedsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _fetchState.emit(
                        BreedListUiState.Success(
                            result.data?.breedList ?: emptyList()
                        )
                    )
                }

                is Resource.Error -> {
                    _fetchState.emit(
                        BreedListUiState.Error(
                            result.message ?: "Something went wrong!"
                        )
                    )
                }

                is Resource.Loading -> {
                    _fetchState.emit(
                        BreedListUiState.Loading
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterBreed(query: String) {
        val breeds = (_fetchState.value as? BreedListUiState.Success)
        filterBreedsUseCase(breeds = breeds?.breeds ?: emptyList(), query = query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _filterState.emit(
                        BreedListFilterUiState.Success(
                            result.data?.breedList ?: emptyList()
                        )
                    )
                }

                is Resource.Error -> {
                    _filterState.emit(
                        BreedListFilterUiState.Error(
                            result.message ?: "Something went wrong!"
                        )
                    )
                }

                is Resource.Loading -> {
                    _filterState.emit(
                        BreedListFilterUiState.Loading
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}