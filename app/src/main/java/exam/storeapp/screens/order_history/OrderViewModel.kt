package exam.storeapp.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import exam.storeapp.data.repositories.OrderRepository
import exam.storeapp.data.models.OrderStatus
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    val orders = OrderRepository.orders

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            OrderRepository.refreshOrders()
        }
    }

    fun updateOrderStatus(orderId: Int, newStatus: OrderStatus) {
        viewModelScope.launch {
            val orderToUpdate = orders.value.find { it.id == orderId }
            if (orderToUpdate != null) {
                val updatedOrder = orderToUpdate.copy(status = newStatus)
                OrderRepository.updateOrder(updatedOrder)
            }
        }
    }

    fun deleteOrder(orderId: Int) {
        viewModelScope.launch {
            OrderRepository.deleteOrder(orderId)
        }
    }
}
