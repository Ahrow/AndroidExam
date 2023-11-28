package exam.storeapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products/?limit=10")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/{id}/")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Response<Product>
}