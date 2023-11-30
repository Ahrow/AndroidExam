package exam.storeapp.data

data class Order (
val id: Int,
val date: String,
val totalPrice: Double,
val items: List<OrderItem>,
val status: OrderStatus
)

data class OrderItem(
    val productId: Int,
    val productTitle: String,
    val productPrice: Double,
    var productCount: Int,
    var totalPrice: Double,
)

enum class OrderStatus{
    PENDING,
    SHIPPED,
    DELIVERED,
}

