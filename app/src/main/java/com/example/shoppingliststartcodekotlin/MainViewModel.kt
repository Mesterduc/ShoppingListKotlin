package com.example.shoppingliststartcodekotlin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository

class MainViewModel : ViewModel() {
    var isProductSorted = false
    var isUnitSorted = false
//    val currentName: MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }

    fun getData(): MutableLiveData<MutableList<Product>> {
        return Repository.getData()
    }

//    fun getCurrentUser(userId: String): String {
//        return Repository.getCurrentUser(userId)
//    }
//    fun getCurrentUserName(userId: String) {
//        val name = Repository.getCurrentUserName(userId)
//        Log.d("TAG", name.value.toString())
//        currentName.setValue("hello")
//    }

    fun add(productName: String, quantity: Int = 1) {
        Repository.addProduct(productName, quantity)
    }

    fun deleteItemAt(productName: String) {
        Repository.deleteProductAt(productName)
    }

    fun getProductAt(position: Int): Product {
        return Repository.getProductAt(position)
    }
    fun changeProduct(productName: String, productUnit: Int){
        Repository.changeProduct(productName, productUnit)
    }

    fun clearList() {
        Repository.clearList()
    }

    fun getList(): MutableList<Product> {
        return Repository.getList()
    }

    fun sortByName() {
        Repository.sortByName(isProductSorted)
        isProductSorted = !isProductSorted
    }

    fun sortByUnits() {
        Repository.sortByUnits(isUnitSorted)
        isUnitSorted = !isUnitSorted
    }


}