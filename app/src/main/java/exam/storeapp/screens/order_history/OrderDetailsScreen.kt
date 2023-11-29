package exam.storeapp.screens.order_history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import exam.storeapp.data.OrderRepository
import exam.storeapp.data.OrderStatus

@Composable
fun OrderDetailsScreen(viewModel: OrderViewModel, orderId: Int) {
    val order = viewModel.orders.collectAsState().value.find { it.id == orderId }
    //TODO STYLE THIS
    if (order != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order ID: ${order.id}", style = MaterialTheme.typography.titleLarge)
            Text("Total Price: ${order.totalPrice}", style = MaterialTheme.typography.bodyMedium)
            Text("Total Items: ${order.items.size}", style = MaterialTheme.typography.bodyMedium)
            Text("Current Status: ${order.status.name}", style = MaterialTheme.typography.bodyMedium)
            Button(onClick = { OrderRepository.cancelOrder(orderId) }) {
                Text(text = "Cancel Order")
            }
            Button(onClick = {
                viewModel.updateOrderStatus(orderId, OrderStatus.DELIVERED)
            }) {
                Text("Mark as Delivered")
            }
        }
    } else {
        Text("Order not found", style = MaterialTheme.typography.bodyMedium, color = Color.Red)
    }
}