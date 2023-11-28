package exam.storeapp.screens.product_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.Product
import exam.storeapp.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val _repository: ProductRepository = ProductRepository
    val products = MutableStateFlow<List<Product>>(emptyList())

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            products.value = _repository.getProducts()
        }
    }
}