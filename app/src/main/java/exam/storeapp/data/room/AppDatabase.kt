package exam.storeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import exam.storeapp.data.Order
import exam.storeapp.data.Product

@Database(
    entities = [Product::class, Order::class],
    version = 2,
    exportSchema = false
)
// COULD add TypeConverters directly on only OrderDao but whatever :)
@TypeConverters(OrderItemTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
}
