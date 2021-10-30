package com.example.shoppingliststartcodekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.databinding.ActivityMainBinding
import com.example.shoppingliststartcodekotlin.databinding.ShoppingItemBinding

class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
   lateinit var adapter: ProductAdapter
   lateinit var binding : ActivityMainBinding
   lateinit var viewModel : MainViewModel

   private lateinit var binding2 : ShoppingItemBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

//        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, container, false)


        viewModel.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            binding.recyclerView
            updateUI(it)
        })

        binding.addProductButton.setOnClickListener {
            viewModel.add()
        }
    }


    fun updateUI(products : MutableList<Product>) {
        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        /*you need to have a defined a recylerView in your
        xml file - in this case the id of the recyclerview should
        be "recyclerView" - as the code line below uses that */

//        binding.recyclerView.layoutManager = layoutManager

       adapter = ProductAdapter(products)

      /*connecting the recyclerview to the adapter  */
        binding.recyclerView.adapter = adapter

    }
}