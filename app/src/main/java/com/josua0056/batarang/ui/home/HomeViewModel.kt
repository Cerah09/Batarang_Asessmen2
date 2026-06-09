package com.josua0056.batarang.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josua0056.batarang.data.UserPreferencesRepository
import com.josua0056.batarang.model.Geometri
import com.josua0056.batarang.model.GeometriRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel(
    geometriRepository: GeometriRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        geometriRepository.getAllGeometriStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    val isLinearLayout: StateFlow<Boolean> = userPreferencesRepository.isLinearLayout
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = true
        )

    fun selectLayout(isLinearLayout: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveLayoutPreference(isLinearLayout)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val itemList: List<Geometri> = listOf())
