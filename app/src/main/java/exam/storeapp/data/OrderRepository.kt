package exam.storeapp.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object OrderRepository {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()
    fun addOrder(order: Order) {
        val updatedOrders = _orders.value.toMutableList().apply {
            add(order)
        }
        _orders.value = updatedOrders
    }
    fun updateOrders(updatedOrders: List<Order>) {
        _orders.value = updatedOrders
    }
    fun cancelOrder(orderId: Int): Result<Unit> {
        val orderExists = _orders.value.any { it.id == orderId }
        if (!orderExists) {
            return Result.failure(NoSuchElementException("Order with ID $orderId not found"))
        }
        val updatedOrders = _orders.value.filterNot { it.id == orderId }
        _orders.value = updatedOrders
        return Result.success(Unit)
    }
}