package exam.storeapp.data.repositories

import android.content.Context
import android.util.Log
import androidx.room.Room
import exam.storeapp.data.Product
import exam.storeapp.data.ProductService
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
        // .fallback... -> Resolved a migration issue I had.
        // used in migration of DB -> if fails, it falls back to destructive migration. Data Loss
        // Should only be used in development not production.
    }

    suspend fun getProducts(): List<Product> {
        try {
            // FETCH from API -> Insert to DB
            val response = _productService.getAllProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    _productDao.insertProducts(it)
                    return it
                }
            } else {
                throw Exception("getProducts() API response is not successful")
            }
        } catch (apiException: Exception) {
            Log.e("getProducts", "Error fetching products from API and inserting into DB", apiException)

            // IF FETCH from API fails -> FETCH from DB
            try {
                val dbProducts = _productDao.getProducts().first()
                if (dbProducts.isNotEmpty()) {
                    return dbProducts
                }
            } catch (dbException: Exception) {
                Log.e("getProducts", "Error getting products from DB", dbException)
            }
        }

        return emptyList()
    }

    fun getProductById(productId: Int): Product? {
        return _productDao.getProductById(productId)
    }
}