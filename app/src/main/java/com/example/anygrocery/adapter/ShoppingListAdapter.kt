package com.example.anygrocery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anygrocery.databinding.ItemShoppingListBinding
import com.example.anygrocery.model.Product

class ShoppingListAdapter(
    private val itemClickCallback: ((Product) -> Unit)?,
    private val itemLongClickCallback: ((Product) -> Unit)?
): RecyclerView.Adapter<ShoppingListAdapter.ProductViewHolder>() {

    private var products = emptyList<Product>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding = ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = products[position]
        holder.bind(current.productName,
                    current.amount,
                    current.isChecked
        )
    }

    inner class ProductViewHolder(private val itemBinding: ItemShoppingListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            val root = itemBinding.root
            root.setOnClickListener {
                itemClickCallback?.invoke(products[adapterPosition])
            }
            root.setOnLongClickListener {
                itemLongClickCallback?.invoke(products[adapterPosition])
                return@setOnLongClickListener true
            }
        }
        fun bind(name: String, amount: Int, isChecked: Boolean?) {
            itemBinding.productName.text = name
            itemBinding.productAmount.text = amount.toString()
            itemBinding.checkBox.isChecked = isChecked!!
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: List<Product>) {
        this.products = newData
        notifyDataSetChanged()
    }
}
