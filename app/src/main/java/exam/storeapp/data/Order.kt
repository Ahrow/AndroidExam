package exam.storeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders")
data class Order (
@PrimaryKey val id: Int,
val date: String,
val totalPrice: Double,
val items: List<OrderItem>,
val status: OrderStatus
)

@Entity(tableName = "order_items")
data class OrderItem(
    val productId: Int,
    val productTitle: String,
    val productPrice: Double,
    var productCount: Int,
    var totalPrice: Double,
)

enum class OrderStatus{
    PENDING,
    DELIVERED,
}

