package com.example.shoppingliststartcodekotlin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.databinding.ActivityMainBinding
import com.example.shoppingliststartcodekotlin.databinding.ShoppingItemBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
    lateinit var adapter: ProductAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getData().observe(this, Observer {
            Log.d("Products", "Found ${it.size} products")
            updateUI(it)
        })

        binding.addProductButton.setOnClickListener {
            val name = binding.productName.text
            val units = binding.productUnits.text
            if(name.isNotEmpty() && units.isNotEmpty()){
                viewModel.add(
                    name.toString(),
                    units.toString().toInt()
                )
                name.clear()
                units.clear()
            } else if (name.isNotEmpty()){
                viewModel.add(
                    name.toString()
                )
                name.clear()
                units.clear()
            }
            else {
                Toast.makeText(this, "name field is empty", Toast.LENGTH_LONG).show()
            }

        }


    }


    fun updateUI(products: MutableList<Product>) {
//        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        /*you need to have a defined a recylerView in your
        xml file - in this case the id of the recyclerview should
        be "recyclerView" - as the code line below uses that */

//        binding.recyclerView.layoutManager = layoutManager

        adapter = ProductAdapter(products)

        // swipe to delete
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val product = viewModel.getProductAt(viewHolder.adapterPosition)
                    val position = viewHolder.adapterPosition
                    viewModel.deleteItemAt(viewHolder.adapterPosition)

                    Snackbar.make(
                        binding.root, // The ID of your coordinator_layout
                        "undo?",
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAction("UNDO") {
                            viewModel.createItemAt(position, product)
                        }
                    }.show()
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        /*connecting the recyclerview to the adapter  */
        binding.recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_clearList -> clearListAlert()
            R.id.nav_share  -> shareList()
        }

        return super.onOptionsItemSelected(item)
    }

    fun shareList(){
        val products = viewModel.getList()
        var productList = "Inkøbs liste: "
        products.forEach{
            productList += "${it.name} - ${it.units}, "
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Shared Data")
        intent.putExtra(Intent.EXTRA_TEXT, productList)
        startActivity(Intent.createChooser(intent, "Share Using"))
    }


    fun clearListAlert() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Clear the shopping list?")
        alert.setMessage("Are you sure you want to clear the list?")
        alert.setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->
        })
        alert.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
            viewModel.clearList()
        })
        alert.show()
    }

}