package com.lloydstest.features.list.presentation.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydstest.features.list.domain.use_case.FetchDogsByBreedUseCase
import com.lloydstest.features.list.presentation.state.GetDogsUiState
import com.lloydstest.network.core.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DogsListViewModel : ViewModel() {

    private val fetchDogsByBreedUseCase = FetchDogsByBreedUseCase()

    private val _filterState = MutableStateFlow<GetDogsUiState>(GetDogsUiState.Loading)
    val filterState = _filterState

    private val _title = mutableStateOf("labrador")
    val title: State<String> = _title

    fun fetchDogs(breed: String = "labrador") {
        _title.value = breed
        fetchDogsByBreedUseCase(breed = breed).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _filterState.emit(
                        GetDogsUiState.Success(
                            result.data?.dogImages ?: emptyList()
                        )
                    )
                }

                is Resource.Error -> {
                    _filterState.emit(
                        GetDogsUiState.Error(
                            result.message ?: "Something went wrong!"
                        )
                    )
                }

                is Resource.Loading -> {
                    _filterState.emit(
                        GetDogsUiState.Loading
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}