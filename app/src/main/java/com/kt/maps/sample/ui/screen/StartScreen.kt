package com.kt.maps.sample.ui.screen

import android.Manifest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kt.maps.sample.R
import com.kt.maps.sample.ui.theme.BackgroundMint
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartScreen(
    startScreenViewModel: StartScreenViewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner)[StartScreenViewModel::class.java]
) {
    val state by startScreenViewModel.screenState.collectAsStateWithLifecycle()

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) {
        startScreenViewModel.sendUiEvent(StartScreenEvent.PermissionChecked)
    }

    LaunchedEffect(key1 = "") {
        startScreenViewModel.permissionEvent.collectLatest {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    when (state) {
        is StartScreenState.Loading -> {

        }

        is StartScreenState.Data -> {
            ListScreen(
                (state as StartScreenState.Data).examples
            ) { example ->
                startScreenViewModel.sendUiEvent(StartScreenEvent.TapExample(example))
            }
        }

        is StartScreenState.Error -> {

        }
    }
}

@Composable
private fun ListScreen(
    contents: List<Example>,
    event: (Example) -> Unit
) {
    Box(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        ExampleSearchBar(contents, event)
        ExamplesColumn(contents, event)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExampleSearchBar(
    examples: List<Example> = emptyList(),
    onSearchItemSelected: (Example) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<Example>() }

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(
                examples.filter {
                    it.category.contains(
                        other = query,
                        ignoreCase = true
                    ) || it.title.contains(
                        other = query,
                        ignoreCase = true
                    ) || it.description.contains(
                        other = query,
                        ignoreCase = true
                    )
                }
            )
        }
    }

    DockedSearchBar(
        modifier = Modifier
            .padding(top = 30.dp, bottom = 30.dp)
            .fillMaxWidth(),
        query = query,
        onQueryChange = { query = it },
        onSearch = { active = false },
        onActiveChange = { active = it },
        active = active,
        placeholder = { Text("Search Examples") },
        leadingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable {
                            active = false
                            query = ""
                        }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }
    ) {
        if (searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = searchResults, key = { it.title }) { example ->
                    ContentItem(example = example, onItemClick = onSearchItemSelected)
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
private fun ExamplesColumn(
    contents: List<Example> = emptyList(),
    onItemTap: (Example) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 110.dp)
    ) {
        ContentColumn(
            modifier = Modifier
                .weight(2f),
            contents.groupBy { it.category },
            onItemTap,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContentColumn(
    modifier: Modifier = Modifier,
    contents: Map<String, List<Example>> = emptyMap(),
    event: (Example) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        contents.forEach { (category, examples) ->

            stickyHeader {
                StickyText(text = category)
            }
            itemsIndexed(examples) { index, item ->
                ContentItem(
                    item,
                    event
                )
                if (index < examples.lastIndex) {
                    Divider(color = Color.Black, thickness = 0.5.dp)
                }
            }
        }

    }
}


@Composable
fun StickyText(text: String) {
    Text(
        modifier = Modifier
            .background(BackgroundMint)
            .padding(vertical = 3.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.labelLarge,
        color = Color.White,

        text = text
    )
}


@Composable
private fun ContentItem(
    example: Example,
    onItemClick: (Example) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(example) }
            .padding(13.dp),
    ) {
        Text(
            text = example.title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            text = example.description
        )
    }


}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewStartScreen() {
    ListScreen(
        emptyList(),
        {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewExamplesColumn() {
    ExamplesColumn(
        contents =
        emptyList(),
        {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewContentItem() {
    ContentItem(
        example = Example(
            "overlay",
            stringResource(id = R.string.marker_activity_label),
            stringResource(id = R.string.marker_activity_description),
            "",
            "",
            false
        ),
        onItemClick = {})
}

@Preview
@Composable
fun previewStickyText() {
    StickyText(text = "lines and polygons")
}