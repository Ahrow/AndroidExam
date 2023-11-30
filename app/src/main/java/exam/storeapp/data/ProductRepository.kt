package exam.storeapp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import exam.storeapp.data.room.AppDatabase
import kotlinx.coroutines.flow.first
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepository {

    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService = _retrofit.create(ProductService::class.java)

    private lateinit var _appDatabase: AppDatabase
    private val _productDao by lazy { _appDatabase.productDao()}

    // INIT DB
    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name ="app-database"
        ).fallbackToDestructiveMigration().build()
    }
    //TODO read documentation on this -> .fallbackToDestructiveMigration() -> Basically CLEARS the DB
    // Instead of migration -> Destroys the DB on rebuild ? ASK in lecture

    suspend fun getProducts(): List<Product> {
        try {
            // CHECK db for products first ->
            val dbProducts = _productDao.getProducts().first()
            if (dbProducts.isNotEmpty()) {
                return dbProducts
            }

            // THEN if no dbProducts -> FETCH from API & INSERT
            val response = _productService.getAllProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    _productDao.insertProducts(it)
                    return it
                }
            } else {
                throw Exception("getProducts() response is not successful")
            }
        } catch (e: Exception){
            Log.e("getProducts", "Error getting products", e)
        }
        return emptyList()
    }

    fun getProductById(productId: Int): Product? {
        return _productDao.getProductById(productId)
    }
}