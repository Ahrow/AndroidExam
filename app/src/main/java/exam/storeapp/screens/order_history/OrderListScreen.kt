package exam.storeapp.screens.order_history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import exam.storeapp.data.Order

@Composable
fun OrdersListScreen(viewModel: OrderViewModel, onOrderSelected: (Int) -> Unit) {
    val orders = viewModel.orders.collectAsState()

    LazyColumn {
        items(orders.value) { order ->
            OrderItemView(order, onOrderSelected)
        }
    }
}

@Composable
fun OrderItemView(order: Order, onOrderSelected: (Int) -> Unit) {
    // Use a Card or a Surface for better UI
    Text(
        text = "Order ID: ${order.id}",
        // Add more styling and other properties
    )
    Text(
        text = "Status: ${order.status.name}",
        // Add more styling and other properties
    )
    // Add a clickable modifier to view order details
    // Example: .clickable { onOrderSelected(order.id) }
}