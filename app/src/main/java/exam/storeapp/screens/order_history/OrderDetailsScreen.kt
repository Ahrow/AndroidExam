package exam.storeapp.screens.order_history

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun OrderDetailsScreen(viewModel: OrderViewModel, orderId: Int) {
    val order = viewModel.orders.collectAsState().value.find { it.id == orderId }
    //TODO STYLE THIS
    if (order != null) {
        Text("Order ID: ${order.id}")
        Text("Total Price: ${order.totalPrice}")
        Text("Total Items: ${order.items}")
        Text("Current Status: ${order.status.name}")
    } else {
        Text("Order not found")
    }
}