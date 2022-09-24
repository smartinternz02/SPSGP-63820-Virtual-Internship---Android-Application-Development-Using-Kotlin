package com.example.grocerryapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GrocerryViewModelFactory (private val repository: GrocerryRepository):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GrocerryViewModel(repository) as T

    }
}