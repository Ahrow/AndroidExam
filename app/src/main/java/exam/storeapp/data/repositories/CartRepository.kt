package exam.storeapp.data.repositories

import android.content.Context
import androidx.room.Room
import exam.storeapp.data.CartItem
import exam.storeapp.data.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object CartRepository {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    private lateinit var _appDatabase: AppDatabase
    private val _cartItemDao by lazy { _appDatabase.cartItemDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app-database"
        ).fallbackToDestructiveMigration().build()
    }

    suspend fun addCartItem(cartItem: CartItem) {
        _cartItemDao.insertCartItem(cartItem)
        refreshCartItems()
    }

   fun calculateTotalSum(): Double {
        return _cartItems.value.sumOf { it.productPrice * it.productCount }
    }

    suspend fun refreshCartItems() {
        _cartItems.value = _cartItemDao.getAllCartItems()
    }

    suspend fun clearCart() {
        _cartItemDao.clearCart()
        refreshCartItems()
    }
}
