import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.models.Product

class ProductAdapter(private val productList: Array<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productId: TextView = itemView.findViewById(R.id.textViewProductId)
        val productName: TextView = itemView.findViewById(R.id.textViewProductName)
        val productCheckCount: TextView = itemView.findViewById(R.id.textViewProductCheckCount)
        val productProductCount: TextView = itemView.findViewById(R.id.textViewProductProductCount)
        val productPurchasePrice: TextView = itemView.findViewById(R.id.textViewProductPurchasePrice)
        val productSalesPrice: TextView = itemView.findViewById(R.id.textViewProductSalesPrice)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.productId.text = currentProduct.id.toString()
        holder.productName.text = currentProduct.name
        holder.productCheckCount.text = currentProduct.checkCount.toString()
        holder.productProductCount.text = currentProduct.productCount.toString()
        holder.productPurchasePrice.text = currentProduct.purchasePrice.toString()
        holder.productSalesPrice.text = currentProduct.salesPrice.toString()


    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
