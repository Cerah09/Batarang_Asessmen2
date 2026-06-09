package com.josua0056.batarang.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.josua0056.batarang.BatarangApplication
import com.josua0056.batarang.ui.home.HomeViewModel
import com.josua0056.batarang.ui.item.ItemEntryViewModel
import com.josua0056.batarang.ui.item.ItemUpdateViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                batarangApplication().container.geometriRepository,
                batarangApplication().userPreferencesRepository
            )
        }
        initializer {
            ItemEntryViewModel(batarangApplication().container.geometriRepository)
        }
        initializer {
            ItemUpdateViewModel(
                this.createSavedStateHandle(),
                batarangApplication().container.geometriRepository
            )
        }
    }
}

fun CreationExtras.batarangApplication(): BatarangApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BatarangApplication)
