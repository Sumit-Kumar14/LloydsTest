package com.lloydstest.features.list.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.lloydstest.features.list.presentation.ui.components.DogListScreen
import com.lloydstest.features.list.presentation.vm.DogsListViewModel
import com.lloydstest.features.search.presentation.ui.activity.BreedSearchActivity

class DogListActivity : ComponentActivity() {

    private val viewModel by viewModels<DogsListViewModel>()

    private val changeBreedLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val breed = result.data?.getStringExtra("breed") ?: "labrador"
                viewModel.fetchDogs(breed)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DogListScreen(
                title = viewModel.title.value,
                dogsUiState = viewModel.filterState.collectAsState().value,
                onChange = {
                    Intent(this, BreedSearchActivity::class.java).also {
                        changeBreedLauncher.launch(it)
                    }
                }
            )
        }

        viewModel.fetchDogs()
    }

}