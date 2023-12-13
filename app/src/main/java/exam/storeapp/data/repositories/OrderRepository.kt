package exam.storeapp.data.repositories

import android.content.Context
import androidx.room.Room
import exam.storeapp.data.models.Order
import exam.storeapp.data.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object OrderRepository {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    private lateinit var _appDatabase: AppDatabase
    private val _orderDao by lazy { _appDatabase.orderDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app-database"
        ).fallbackToDestructiveMigration()
            .build()
    }
    suspend fun refreshOrders() {
        _orders.value = _orderDao.getAllOrders()
    }

    suspend fun addOrder(order: Order) {
        _orderDao.insertOrder(order)
        refreshOrders()
    }

    suspend fun updateOrder(order: Order) {
        _orderDao.updateOrder(order)
        refreshOrders()
    }

    suspend fun deleteOrder(orderId: Int) {
        _orderDao.deleteOrder(orderId)
        refreshOrders()
    }
}