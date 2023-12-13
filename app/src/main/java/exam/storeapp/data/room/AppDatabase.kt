package exam.storeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import exam.storeapp.data.dao.CartItemDao
import exam.storeapp.data.dao.OrderDao
import exam.storeapp.data.dao.ProductDao
import exam.storeapp.data.models.CartItem
import exam.storeapp.data.models.Order
import exam.storeapp.data.models.Product
import exam.storeapp.utillity.OrderItemTypeConverter

@Database(
    entities = [Product::class, Order::class, CartItem::class],
    version = 1,
    exportSchema = false
)
// COULD add TypeConverters directly on only OrderDao but now its available for all Dao's:)
@TypeConverters(OrderItemTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun cartItemDao(): CartItemDao
}
