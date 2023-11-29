package exam.storeapp.screens.shopping_cart

import androidx.lifecycle.ViewModel
import exam.storeapp.data.Order
import exam.storeapp.data.OrderItem
import exam.storeapp.data.OrderRepository
import exam.storeapp.data.OrderStatus
import exam.storeapp.data.Product
import exam.storeapp.screens.order_history.OrderIdAndDateGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//TODO LET USER ADJUST QUANTITY OF EACH PRODUCT ADDED
class ShoppingCartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()


    val totalSum: Double
        get() = _cartItems.value.sumOf { it.totalPrice }
    fun addToCart(product: Product, count: Int = 1) {
        val updatedList = _cartItems.value.toMutableList()
        val existingItem = updatedList.find { it.productId == product.id }
        if (existingItem != null) {
            // Update quantity and total price //TODO NEED TO RESET PRICE WHEN COMPLETE PURCHASE
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
                    totalPrice = product.price * count
                )
            )
        }
        _cartItems.value = updatedList
    }
    fun completePurchase() {
        val newOrder = Order(
            id = OrderIdAndDateGenerator.generateOrderId(),
            date = OrderIdAndDateGenerator.getCurrentDate(),
            totalPrice = totalSum,
            items = _cartItems.value,
            status = OrderStatus.PENDING
        )

        OrderRepository.addOrder(newOrder)
        _cartItems.value = emptyList()
    }
}