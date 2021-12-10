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

    // late var for the interface
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(productName: String, productUnit: Int)
    }

    // setting the mListener
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //The context refers to the ui parent so to speak
    private lateinit var context: Context

    private lateinit var binding: ShoppingItemBinding

    // product itemview
    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(product: Product) {
            itemView.textViewNameeeeee.text = product.name.toString()
            itemView.textViewQuantity.text = product.units.toString()
        }
        // runs on every product
        init {
            // gets data for the product you clikced on
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

        // return the adapter and our viewHolder with the view and data
        return ProductAdapter(products).ViewHolder(itemView = v, mListener)

    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bindItems(products[position])
    }

    override fun getItemCount(): Int {
         return products.size
    }

}
