package com.example.shoppingliststartcodekotlin.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Repository {
    var products = mutableListOf<Product>()

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()
    private val db = Firebase.firestore
    private val docRef = db.collection("Lists").document("rema")

    fun getData(): MutableLiveData<MutableList<Product>> {
        if (products.isEmpty())
            docRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    products.clear()
                    snapshot.data?.forEach {
//                        Log.d("TAG", "${it.key} + ${it.value}")
                        products.add(Product(it.key, it.value.toString().toInt()))
                        productListener.value = products
                    }
//                    val data = snapshot.get("list") as Map<String, Int>
                } else {
                    Log.d("TAG", "Current data: null")
                }
            }
        return productListener
    }

    fun addProduct(productName: String, quantity: Int = 1) {
        val newProduct = hashMapOf(productName to quantity)
        docRef.set(newProduct, SetOptions.merge())
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
        productListener.value = products
    }

    fun deleteProductAt(productName: String) {
        val updates = hashMapOf<String, Any>(
            productName to FieldValue.delete()
        )
        docRef.update(updates).addOnCompleteListener { }
        productListener.value = products
    }

    fun getProductAt(position: Int): Product {
        return products.get(position)
    }

    fun clearList() {
        docRef.get().addOnSuccessListener { hej ->
            hej.data?.forEach {
            Log.d("TAG", hej.data.toString())
                docRef.update(it.key.toString(), FieldValue.delete()).addOnCompleteListener{
                    productListener.value = products
                }
            }
        }
    }

    fun getList(): MutableList<Product> {
        return products
    }

    fun sortByName(sorted: Boolean){
        if (sorted)  products.sortBy { it.name } else products.sortByDescending { it.name }
        productListener.value = products
    }

    fun sortByUnits(sorted: Boolean){
        if (sorted)  products.sortBy { it.units } else products.sortByDescending { it.units }
        productListener.value = products
    }

}