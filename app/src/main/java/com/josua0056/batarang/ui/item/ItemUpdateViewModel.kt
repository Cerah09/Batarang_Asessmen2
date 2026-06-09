package com.josua0056.batarang.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josua0056.batarang.model.GeometriRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an item from the [GeometriRepository]'s data source.
 */
class ItemUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val geometriRepository: GeometriRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[ItemUpdateDestination.itemIdArg])

    init {
        viewModelScope.launch {
            itemUiState = geometriRepository.getGeometriStream(itemId)
                .filterNotNull()
                .first()
                .toItemDetails()
                .let { ItemUiState(itemDetails = it, isEntryValid = true) }
        }
    }

    /**
     * Update the item in the Room database
     */
    suspend fun updateItem() {
        if (validateInput(itemUiState.itemDetails)) {
            geometriRepository.updateGeometri(itemUiState.itemDetails.toGeometri())
        }
    }

    /**
     * Deletes the item from the Room database
     */
    suspend fun deleteItem() {
        geometriRepository.deleteGeometri(itemUiState.itemDetails.toGeometri())
    }

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            nama.isNotBlank() && deskripsi.isNotBlank() && jenis.isNotBlank() && rumus.isNotBlank()
        }
    }
}
