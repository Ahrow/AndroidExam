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
    Text(
        text = "Order ID: ${order.id}",

    )
    Text(text = "Order date: ${order.date}")
    Text(
        text = "Status: ${order.status.name}",
    )
  //TODO MAKE CLICKABLE .clickable { onOrderSelected(order.id) -> GOTO Details + STYLE
}