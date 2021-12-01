package com.example.shoppingliststartcodekotlin.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Repository {
    var products = mutableListOf<Product>()

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()
//    val db = Firebase.firestore

    fun getData(): MutableLiveData<MutableList<Product>> {
        if (products.isEmpty())
//            db.collection("Lists").document("hej")
//                .get()
//                .addOnSuccessListener { result ->
//
//                .addOnFailureListener { exception ->
//                    Log.w("hej", "Error getting documents.", exception)
//                }

            createTestData()
        productListener.value = products //we inform the listener we have new data
        return productListener
    }

    fun addProduct(productName: String, quantity: Int = 1){
        Log.d("hej","create repo")
        products.add(Product(name=productName, units=quantity))
        productListener.value = products
    }

    fun deleteProductAt(position: Int){
        Log.d("hej","delete product")
        products.removeAt(position)
        productListener.value = products
    }

    fun getProductAt(position: Int): Product {
        return products.get(position)
    }

    fun createProductAt(position: Int, item: Product){
        products.add(position,Product(item.name, item.units))
        productListener.value = products
    }

    fun clearList(){
        products.clear()
        productListener.value = products
    }

    fun getList(): MutableList<Product>{
        return products
    }


    fun createTestData()
    {
        //add some products to the products list - for testing purposes
        Log.d("Repository","create testdata")

        products.add(Product(name="tomater", units = 1))
        products.add(Product(name="bønner", units = 3))

    }

}