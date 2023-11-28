package exam.storeapp.data

data class Order (
val id: Int,
val date: String,
val totalPrice: Double,
val items: List<OrderItem>
)

data class OrderItem(
    val productId: Int,
    val productTitle: String,
    val productCount: Int,
    val productPrice: Double,
)

