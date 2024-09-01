package com.lloydstest.features.list.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.lloydstest.features.list.presentation.model.GetDogsUiState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListScreen(
    title: String,
    dogsUiState: GetDogsUiState,
    onChange: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = onChange,
                        content = {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Change the breed"
                            )
                        }
                    )
                }
            )
        },
    ) { contentPadding ->
        when (dogsUiState) {
            is GetDogsUiState.Error -> {

            }

            is GetDogsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is GetDogsUiState.Success -> {
                ImagesGrid(contentPadding, dogsUiState.dogImages)
            }
        }
    }
}

@Composable
private fun ImagesGrid(paddingValues: PaddingValues, images: List<String>) {
    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        items(images, key = { it }) {
            Surface(
                tonalElevation = 3.dp,
                modifier = Modifier.aspectRatio(1f)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }
        }
    }
}