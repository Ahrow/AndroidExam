package exam.storeapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products/?limit=20")
    suspend fun getAllProducts(): Response<List<Product>>

    // NO Longer needed since handled offline instead
    @GET("products/{id}/")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Response<Product>
}