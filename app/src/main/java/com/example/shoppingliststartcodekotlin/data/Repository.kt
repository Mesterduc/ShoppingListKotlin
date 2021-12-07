package com.example.shoppingliststartcodekotlin.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Repository {
    var products = mutableListOf<Product>()

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()
    private var nameListener = MutableLiveData<String>()

    private val db = Firebase.firestore
    private var auth = Firebase.auth
    private val user = db.collection("Users")
//    private val docRef = db.collection("Lists").document("rema")
    private val loggedInUser = user.document(auth.currentUser?.uid.toString()).collection("Lists").document("list")

    fun changeProduct(productName: String, productUnit: Int){
        loggedInUser.update(productName, productUnit + 1)
        productListener.value = products
    }

    fun getData(): MutableLiveData<MutableList<Product>> {
//        if (products.isEmpty())
            loggedInUser.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    products.clear()
                    snapshot.data?.forEach {
                        products.add(Product(it.key, it.value.toString().toInt()))
                        productListener.value = products
                    }
                } else {
                    Log.d("TAG", "Current data: null")
                }
            }
        return productListener
    }

//    fun getCurrentUserName(userId: String): MutableLiveData<String>{
//        user.get().addOnSuccessListener { users ->
////            Log.d("TAG", users.g)
//            users.forEach{
////                Log.d("TAG", userId)
////                Log.d("TAG", it.id.toString())
//                if( it.id.toString() == userId.toString()){
//                    Log.d("TAG", it.getString("User").toString())
//                    nameListener.value = it.getString("User").toString()
//
//
//                }
//            }
//
//        }
//        return nameListener
//    }

//    fun getCurrentUser(userId: String): String{
//        var name = ""
//
//        user.get().addOnSuccessListener { users ->
////            Log.d("TAG", users.g)
//            users.forEach{
//            Log.d("TAG", userId)
//                Log.d("TAG", it.id.toString())
//                if( it.id.toString() == userId.toString()){
//                    Log.d("TAG", it.getString("User").toString())
//                    name = it.getString("User").toString()
//
//                }
//            }
//
//        }
//        return name
//    }

    fun addProduct(productName: String, quantity: Int = 1) {
        val newProduct = hashMapOf(productName to quantity)
        loggedInUser.set(newProduct, SetOptions.merge())
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
        productListener.value = products
    }

    fun deleteProductAt(productName: String) {
        val updates = hashMapOf<String, Any>(
            productName to FieldValue.delete()
        )
        loggedInUser.update(updates).addOnCompleteListener { }
        productListener.value = products
    }

    fun getProductAt(position: Int): Product {
        return products.get(position)
    }

    fun clearList() {
        loggedInUser.get().addOnSuccessListener { hej ->
            hej.data?.forEach {
//            Log.d("TAG", hej.data.toString())
                loggedInUser.update(it.key.toString(), FieldValue.delete()).addOnCompleteListener{
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