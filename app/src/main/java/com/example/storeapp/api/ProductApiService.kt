import com.example.storeapp.models.Product
import com.example.storeapp.models.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductApiService {

    @GET("v1/product")
    fun getProducts(): Call<ProductResponse>
}
