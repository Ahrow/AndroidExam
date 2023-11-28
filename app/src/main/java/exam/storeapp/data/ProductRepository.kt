package exam.storeapp.data

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
        val response = _productService.getAllProducts()
        if (response.isSuccessful) {
            return response.body()?: emptyList()
        } else {
            throw Exception("Error")
        }
    }

    suspend fun getProductById(productId: Int): Product {
        val response = _productService.getProduct(productId)
        if (response.isSuccessful) {
            return response.body()!!

        } else {
            throw Exception("Error fetching product details")
        }
    }


//    fun getProducts(): Flow<List<Product>> = flow {
//        val response = _productService.getAllProducts()
//        if (response.isSuccessful) {
//            response.body()?.results?.let { products ->
//                emit(products)
//            } ?: throw Exception("Failed: Empty response body")
//        } else {
//            throw Exception("Response was not successful")
//        }
//    }.flowOn(Dispatchers.IO)
}