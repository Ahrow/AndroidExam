package exam.storeapp.screens.product_overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.Product
import exam.storeapp.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val _repository: ProductRepository = ProductRepository
    val products = MutableStateFlow<List<Product>>(emptyList())

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val filteredProducts = products.combine(searchQuery) { products, query ->
        if (query.isBlank()) products
        else products.filter { it.title.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        Log.d("ProductListViewModel", "Search query updated: $query")
        Log.d("ProductListViewModel", "Filtered products count: ${filteredProducts.value.size}")
    }
    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            products.value = _repository.getProducts()
            _loading.value = false
        }
    }
}