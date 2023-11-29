package exam.storeapp.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.OrderRepository
import exam.storeapp.data.OrderStatus
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
 val orders = OrderRepository.orders

    // Function to update the status of an order
    fun updateOrderStatus(orderId: Int, newStatus: OrderStatus) {
        viewModelScope.launch {
            val updatedOrders = orders.value.map { order ->
                if (order.id == orderId) {
                    order.copy(status = newStatus)
                } else {
                    order
                }
            }
            OrderRepository.updateOrders(updatedOrders)
        }
    }

    // Function to simulate status change on an order
    fun simulateStatusChange(orderId: Int) {
        viewModelScope.launch {
            val updatedOrders = orders.value.map { order ->
                if (order.id == orderId) {
                    val newStatus = when (order.status) {
                        OrderStatus.PENDING -> OrderStatus.SHIPPED
                        OrderStatus.SHIPPED -> OrderStatus.DELIVERED
                        else -> order.status
                    }
                    order.copy(status = newStatus)
                } else {
                    order
                }
            }
            OrderRepository.updateOrders(updatedOrders)
        }
    }
}
