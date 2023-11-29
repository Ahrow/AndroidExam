package exam.storeapp.data

import android.util.Log
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

   suspend fun getProducts(): List<Product> {
       try {
           val response = _productService.getAllProducts()
           if (response.isSuccessful) {
               return response.body()?: emptyList()
           } else {
               throw Exception("getProducts() response is not successful")
           }
       } catch (e: Exception){
           Log.e("getProducts", "Error getting products", e)
       }
        return emptyList()
    }

    suspend fun getProductById(productId: Int): Product? {
        try {
            val response = _productService.getProduct(productId)
            if (response.isSuccessful) {
                return response.body()!!

            } else {
                throw Exception("Error fetching product details")
            }
        } catch (e: Exception) {
            Log.e("getProductsById", "Error getting product by id", e)
        }
        return null
    }
}