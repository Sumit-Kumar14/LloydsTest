package com.lloydstest.features.search.presentation.state

sealed class BreedListUiState {
    data object Loading : BreedListUiState()
    data class Success(val breeds: List<String>) : BreedListUiState()
    data class Error(val message: String) : BreedListUiState()
}