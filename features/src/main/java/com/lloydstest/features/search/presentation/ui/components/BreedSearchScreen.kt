package com.lloydstest.features.search.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lloydstest.features.search.presentation.state.BreedListFilterUiState
import com.lloydstest.features.search.presentation.state.BreedListUiState
import java.util.Locale

@Composable
fun BreedSearchScreen(
    breedListUiState: BreedListUiState,
    filterUiState: BreedListFilterUiState,
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            EmbeddedSearchBar(
                filterUiState = filterUiState,
                onQueryChange = onQueryChange,
                isSearchActive = isSearchActive,
                onSearch = onSearch,
                onBack = onBack,
                onActiveChanged = { isSearchActive = it },
            )
        },
    ) { contentPadding ->
        BreedList(
            contentPadding = contentPadding,
            breedListUiState = breedListUiState,
            onSearch = onSearch
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EmbeddedSearchBar(
    filterUiState: BreedListFilterUiState,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    isSearchActive: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: ((String) -> Unit) = { },
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val activeChanged: (Boolean) -> Unit = { active ->
        searchQuery = ""
        onQueryChange("")
        onActiveChanged(active)
    }
    val breedListUiState = filterUiState as? BreedListFilterUiState.Success
    SearchBar(
        query = searchQuery,
        onQueryChange = { query ->
            searchQuery = query
            onQueryChange(query)
        },
        onSearch = onSearch,
        active = isSearchActive,
        onActiveChange = activeChanged,
        modifier = modifier
            .padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        placeholder = { Text("Search") },
        leadingIcon = {
            if (isSearchActive) {
                Icon(
                    modifier = Modifier.clickable {
                        onBack.invoke()
                    },
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        tonalElevation = 0.dp,
    ) {
        LazyColumn {
            items(breedListUiState?.breeds ?: emptyList()) { breed ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple()
                        ) {
                            onSearch.invoke(breed)
                        }
                        .padding(16.dp),
                    text = breed.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                )
            }
        }
    }
}

@Composable
private fun BreedList(
    contentPadding: PaddingValues,
    breedListUiState: BreedListUiState,
    onSearch: (String) -> Unit = {},
) {
    when (breedListUiState) {
        is BreedListUiState.Error -> {
            Text(text = breedListUiState.message)
        }

        BreedListUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is BreedListUiState.Success -> {
            LazyColumn(
                modifier = Modifier.padding(contentPadding)
            ) {
                items(breedListUiState.breeds) { breed ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple()
                            ) {
                                onSearch.invoke(breed)
                            }
                            .padding(16.dp),
                        text = breed.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    )
                }
            }
        }
    }
}
