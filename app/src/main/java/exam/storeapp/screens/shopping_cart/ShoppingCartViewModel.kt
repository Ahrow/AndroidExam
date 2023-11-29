package exam.storeapp.screens.shopping_cart

import androidx.lifecycle.ViewModel
import exam.storeapp.data.OrderItem
import exam.storeapp.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShoppingCartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()
    val totalSum: Double
        get() = _cartItems.value.sumOf { it.totalPrice }



    fun addToCart(product: Product, count: Int = 1) {
        val updatedList = _cartItems.value.toMutableList()
        val existingItem = updatedList.find { it.productId == product.id }
        if (existingItem != null) {
            // Update quantity and total price
            existingItem.productCount += count
            existingItem.totalPrice = existingItem.productCount * product.price
        } else {
            // Add new item to the cart
            updatedList.add(
                OrderItem(
                    productId = product.id,
                    productTitle = product.title,
                    productCount = count,
                    productPrice = product.price,
                    totalPrice = product.price * count // Add totalPrice here
                )
            )
        }
        _cartItems.value = updatedList
    }
}