package exam.storeapp.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.CartItem
import exam.storeapp.data.Order
import exam.storeapp.data.OrderItem
import exam.storeapp.data.OrderStatus
import exam.storeapp.data.Product
import exam.storeapp.data.repositories.CartRepository
import exam.storeapp.data.repositories.OrderRepository
import exam.storeapp.utillity.OrderIdAndDateGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartViewModel : ViewModel() {
    val cartItems = CartRepository.cartItems
    private val _totalSum = MutableStateFlow(0.0)
    val totalSum: StateFlow<Double> = _totalSum.asStateFlow()


    init {
        viewModelScope.launch {
            CartRepository.refreshCartItems()
        }
    }

    private fun updateTotalSum() {
        viewModelScope.launch {
            _totalSum.value = CartRepository.calculateTotalSum()
        }
    }

    fun addToCart(product: Product, count: Int = 1) {
        viewModelScope.launch {
            val existingCartItem = cartItems.value.find { it.productId == product.id }
            if (existingCartItem != null) {
                // Update quantity of existing item
                val updatedCartItem = existingCartItem.copy(productCount = existingCartItem.productCount + count)
                CartRepository.addCartItem(updatedCartItem)
                updateTotalSum()
            } else {
                // Add new item to the cart
                val newCartItem = CartItem(
                    productId = product.id,
                    productTitle = product.title,
                    productPrice = product.price,
                    productCount = count
                )
                CartRepository.addCartItem(newCartItem)
                updateTotalSum()
            }
        }
    }

    fun increaseCartItemQuantity(cartItemId: Int) {
        viewModelScope.launch {
            val cartItem = cartItems.value.find { it.id == cartItemId }
            if (cartItem != null) {
                val updatedCartItem = cartItem.copy(productCount = cartItem.productCount + 1)
                CartRepository.addCartItem(updatedCartItem)
                updateTotalSum()
            }
        }
    }
    fun decreaseCartItemQuantity(cartItemId: Int) {
        viewModelScope.launch {
            val cartItem = cartItems.value.find { it.id == cartItemId }
            if (cartItem != null && cartItem.productCount > 1) {
                val updatedCartItem = cartItem.copy(productCount = cartItem.productCount - 1)
                CartRepository.addCartItem(updatedCartItem)
                updateTotalSum()
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
                totalPrice = _totalSum.value,
                items = orderItems,
                status = OrderStatus.PENDING
            )

            OrderRepository.addOrder(newOrder)
            CartRepository.clearCart()
            _totalSum.value = 0.0
        }
    }
    fun clearCart() {
        viewModelScope.launch {
            CartRepository.clearCart()
        }
    }
}