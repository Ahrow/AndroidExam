package exam.storeapp.screens.product_details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.Product
import exam.storeapp.data.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
//TODO ADD rating/comment/review + favorite
class ProductDetailsViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
                _loading.value = true
                _selectedProduct.value = ProductRepository.getProductById(productId)
                _loading.value = false
        }
    }
}