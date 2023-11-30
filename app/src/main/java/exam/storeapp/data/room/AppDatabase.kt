package exam.storeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import exam.storeapp.data.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
}