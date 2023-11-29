package exam.storeapp.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.Order
import exam.storeapp.data.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    // Function to update the status of an order
    fun updateOrderStatus(orderId: Int, newStatus: OrderStatus) {
        viewModelScope.launch {
            val updatedOrders = _orders.value.map { order ->
                if (order.id == orderId) {
                    order.copy(status = newStatus)
                } else {
                    order
                }
            }
            _orders.value = updatedOrders
        }
    }

    // Other functions to handle orders...
}
