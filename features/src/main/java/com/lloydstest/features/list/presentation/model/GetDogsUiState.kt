package com.lloydstest.features.list.presentation.model

sealed class GetDogsUiState {
    data object Loading : GetDogsUiState()
    data class Success(val dogImages: List<String>) : GetDogsUiState()
    data class Error(val message: String) : GetDogsUiState()
}