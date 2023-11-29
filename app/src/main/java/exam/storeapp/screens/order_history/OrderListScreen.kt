package exam.storeapp.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import exam.storeapp.data.Order

@Composable
fun OrdersListScreen(viewModel: OrderViewModel, onOrderSelected: (Int) -> Unit) {
    val orders = viewModel.orders.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Orders:",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(orders.value) { order ->
                OrderItemView(order, onOrderSelected)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            //TODO UPDATE THIS TO BE FOR ALL ORDERS INSTEAD OF ITEM
        }) {
            Text("Simulate Status Change for All Orders")
        }
    }
}

@Composable
fun OrderItemView(order: Order, onOrderSelected: (Int) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onOrderSelected(order.id) }
        .padding(16.dp)
    ) {
        Text(text = "Order ID: ${order.id}")
        Text(text = "Order date: ${order.date}")
        Text(text = "Status: ${order.status.name}")
    }
}