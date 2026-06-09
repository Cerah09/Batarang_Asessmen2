package com.josua0056.batarang.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.josua0056.batarang.model.Geometri
import com.josua0056.batarang.model.GeometriRepository

/**
 * ViewModel to validate and insert items in the Room database.
 */
class ItemEntryViewModel(private val geometriRepository: GeometriRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /**
     * Inserts a [Geometri] in the Room database
     */
    suspend fun saveItem() {
        if (validateInput()) {
            geometriRepository.insertGeometri(itemUiState.itemDetails.toGeometri())
        }
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            nama.isNotBlank() && deskripsi.isNotBlank() && jenis.isNotBlank() && rumus.isNotBlank()
        }
    }
}

/**
 * Represents Ui State for an Item.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val nama: String = "",
    val deskripsi: String = "",
    val jenis: String = "",
    val rumus: String = ""
)

/**
 * Extension function to convert [ItemDetails] to [Geometri].
 */
fun ItemDetails.toGeometri(): Geometri = Geometri(
    id = id,
    nama = nama,
    deskripsi = deskripsi,
    jenis = jenis,
    rumus = rumus
)

/**
 * Extension function to convert [Geometri] to [ItemDetails].
 */
fun Geometri.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    nama = nama,
    deskripsi = deskripsi,
    jenis = jenis,
    rumus = rumus
)
