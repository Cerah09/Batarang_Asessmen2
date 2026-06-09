package com.josua0056.batarang.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josua0056.batarang.R
import com.josua0056.batarang.model.Geometri
import com.josua0056.batarang.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val isLinearLayout by viewModel.isLinearLayout.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(HomeDestination.titleRes)) },
                actions = {
                    IconButton(onClick = { viewModel.selectLayout(!isLinearLayout) }) {
                        Icon(
                            imageVector = if (isLinearLayout) Icons.Filled.Menu else Icons.Filled.List,
                            contentDescription = stringResource(if (isLinearLayout) R.string.grid_layout else R.string.linear_layout)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_geometri)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        HomeBody(
            itemList = homeUiState.itemList,
            isLinearLayout = isLinearLayout,
            onItemClick = navigateToItemUpdate,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Geometri>,
    isLinearLayout: Boolean,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        if (itemList.isEmpty()) {
            EmptyState()
        } else {
            if (isLinearLayout) {
                GeometriList(
                    itemList = itemList,
                    onItemClick = { onItemClick(it.id) },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            } else {
                GeometriGrid(
                    itemList = itemList,
                    onItemClick = { onItemClick(it.id) },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Using default icon as placeholder
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = stringResource(R.string.no_data_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.no_data_description),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun GeometriList(
    itemList: List<Geometri>,
    onItemClick: (Geometri) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 80.dp) // Requirement 1d: FAB not blocking last item
    ) {
        items(items = itemList, key = { it.id }) { item ->
            GeometriItem(
                geometri = item,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun GeometriGrid(
    itemList: List<Geometri>,
    onItemClick: (Geometri) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 80.dp) // Requirement 1d: FAB not blocking last item
    ) {
        items(items = itemList, key = { it.id }) { item ->
            GeometriItem(
                geometri = item,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun GeometriItem(
    geometri: Geometri,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = geometri.nama,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = geometri.jenis,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = geometri.rumus,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
