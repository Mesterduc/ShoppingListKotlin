package com.example.shoppingliststartcodekotlin

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
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
import com.example.shoppingliststartcodekotlin.login.LoginActivity
import com.example.shoppingliststartcodekotlin.settings.SettingsActivity
import com.example.shoppingliststartcodekotlin.settings.SettingsHandler
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
    lateinit var adapter: ProductAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var auth: FirebaseAuth
    private val RESULT_CODE_PREFERENCES = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // auth
        auth = Firebase.auth

        // view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // set background color
        view.setBackgroundColor(Color.parseColor(SettingsHandler.getColor(this)))
//        view.setBackgroundColor(Color.WHITE)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        // bind view elements
        binding.addProductButton.setOnClickListener {
            val name = binding.productName.text
            val units = binding.productUnits.text
            if (name.isNotEmpty() && units.isNotEmpty()) {
                viewModel.add(name.toString(), units.toString().toInt())
                name.clear()
                units.clear()
            } else if (name.isNotEmpty()) {
                viewModel.add(
                    name.toString()
                )
                name.clear()
                units.clear()
            } else {
                Toast.makeText(this, "name field is empty", Toast.LENGTH_LONG).show()
            }

        }

        binding.productNameBtn.setOnClickListener {
            viewModel.sortByName()
        }

        binding.productUnitsBtn.setOnClickListener {
            viewModel.sortByUnits()
        }

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        } else {
            viewModel.getData().observe(this, Observer {
                updateUI(it)
            })
            Toast.makeText(
                this, "Welcome",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RESULT_CODE_PREFERENCES)
        //the code means we came back from settings
        {
            val color = SettingsHandler.getColor(this)
            val message = "Background color code is: $color"
            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
            toast.show()
            binding.root.setBackgroundColor(Color.parseColor(SettingsHandler.getColor(this)))
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun changeProduct(oldName: String, name: String, unit: Int) {
        viewModel.changeProduct(oldName, name, unit)
    }

    fun updateUI(products: MutableList<Product>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = ProductAdapter(products)
        adapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener {
            override fun onItemClick(productName: String, productUnit: Int) {
                val dialog = Dialog(oldName = productName, oldUnit = productUnit, ::changeProduct)
                dialog.show(supportFragmentManager, "dialogFragment")
            }
        })

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
                    viewModel.deleteItemAt(product.name)

                    Snackbar.make(
                        binding.root, // The ID of your coordinator_layout
                        "Do you want to undo the delete?",
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAction("UNDO") {
                            viewModel.add(product.name, product.units)
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
            R.id.nav_share -> shareList()
            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivityForResult(intent, RESULT_CODE_PREFERENCES)
            }
            R.id.nav_logout -> {
                auth.signOut()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun shareList() {
        val products = viewModel.getList()
        var productList = "Inkøbs liste: "
        products.forEach {
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

    override fun onBackPressed() {
//        super.onBackPressed()
    }

}