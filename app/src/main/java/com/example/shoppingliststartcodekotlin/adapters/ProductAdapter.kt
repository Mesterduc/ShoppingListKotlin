package com.example.shoppingliststartcodekotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.data.Product
import kotlinx.android.synthetic.main.shopping_item.view.*
import com.example.shoppingliststartcodekotlin.databinding.ShoppingItemBinding
import com.google.android.material.snackbar.Snackbar


class ProductAdapter(var products: MutableList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(productName: String, productUnit: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //The context refers to the ui parent so to speak
    private lateinit var context: Context

    //This is a set of the items we have in our collection
//    private lateinit var binding: RecyclerView
    private lateinit var binding: ShoppingItemBinding

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(product: Product) {
            itemView.textViewNameeeeee.text = product.name.toString()
            itemView.textViewQuantity.text = product.units.toString()
        }
        init {
            itemView.setOnClickListener {
                val product = products[adapterPosition]
                listener.onItemClick(product.name.toString(), product.units)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        context = parent.context
        binding = ShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductAdapter(products).ViewHolder(itemView = v, mListener)

    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bindItems(products[position])
    }

    override fun getItemCount(): Int {
         return products.size
    }

}
