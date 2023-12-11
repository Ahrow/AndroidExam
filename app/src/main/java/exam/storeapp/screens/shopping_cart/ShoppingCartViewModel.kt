package exam.storeapp.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.CartItem
import exam.storeapp.data.repositories.CartRepository
import exam.storeapp.data.Order
import exam.storeapp.data.OrderItem
import exam.storeapp.data.repositories.OrderRepository
import exam.storeapp.data.OrderStatus
import exam.storeapp.data.Product
import exam.storeapp.screens.order_history.OrderIdAndDateGenerator
import kotlinx.coroutines.launch

//TODO LET USER ADJUST QUANTITY OF EACH PRODUCT ADDED
//TODO CONSIDER SAVING cart to DB to keep it when app is destroyed

class ShoppingCartViewModel : ViewModel() {
    val cartItems = CartRepository.cartItems

    val totalSum: Double
        get() = cartItems.value.sumOf { it.productPrice * it.productCount }

    init {
        viewModelScope.launch {
            CartRepository.refreshCartItems()
        }
    }
    fun addToCart(product: Product, count: Int = 1) {
        viewModelScope.launch {
            val existingCartItem = cartItems.value.find { it.productId == product.id }
            if (existingCartItem != null) {
                // Update quantity and total price in the existing cart item
                val updatedCartItem = existingCartItem.copy(productCount = existingCartItem.productCount + count)
                CartRepository.addCartItem(updatedCartItem)
            } else {
                // Add new item to the cart
                val newCartItem = CartItem(
                    productId = product.id,
                    productTitle = product.title,
                    productPrice = product.price,
                    productCount = count
                )
                CartRepository.addCartItem(newCartItem)
            }
        }
    }
    fun completePurchase() {
        viewModelScope.launch {
            val orderItems = cartItems.value.map { cartItem ->
                OrderItem(
                    productId = cartItem.productId,
                    productTitle = cartItem.productTitle,
                    productPrice = cartItem.productPrice,
                    productCount = cartItem.productCount,
                    totalPrice = cartItem.productPrice * cartItem.productCount
                )
            }

            val newOrder = Order(
                id = OrderIdAndDateGenerator.generateOrderId(),
                date = OrderIdAndDateGenerator.getCurrentDate(),
                totalPrice = totalSum,
                items = orderItems,
                status = OrderStatus.PENDING
            )

            OrderRepository.addOrder(newOrder)
            CartRepository.clearCart()
        }
    }
}