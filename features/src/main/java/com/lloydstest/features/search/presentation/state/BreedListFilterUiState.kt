package com.lloydstest.features.search.presentation.state

sealed class BreedListFilterUiState {
    data object Loading : BreedListFilterUiState()
    data class Success(val breeds: List<String>) : BreedListFilterUiState()
    data class Error(val message: String) : BreedListFilterUiState()
}