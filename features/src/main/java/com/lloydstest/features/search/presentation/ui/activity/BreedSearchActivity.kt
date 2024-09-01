package com.lloydstest.features.search.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.lloydstest.features.search.presentation.ui.components.BreedSearchScreen
import com.lloydstest.features.search.presentation.vm.BreedsSearchViewModel


class BreedSearchActivity : ComponentActivity() {
    private val viewModel by viewModels<BreedsSearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BreedSearchScreen(
                viewModel.fetchState.collectAsState().value,
                viewModel.filterState.collectAsState().value,
                onQueryChange = {
                    viewModel.filterBreed(query = it)
                },
                onBack = {
                    onBackPressedDispatcher.onBackPressed()
                },
                onSearch = {
                    val resultIntent = Intent()
                    resultIntent.putExtra("breed", it)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            )
        }

        viewModel.fetchBreeds()
    }

}