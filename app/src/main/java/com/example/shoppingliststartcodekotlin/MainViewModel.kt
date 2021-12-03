package com.example.shoppingliststartcodekotlin

import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository

class MainViewModel : ViewModel() {

    fun getData(): MutableLiveData<MutableList<Product>> {
        return Repository.getData()
    }

    fun add(productName: String, quantity: Int = 1) {
        Repository.addProduct(productName, quantity)
    }

    fun deleteItemAt(productName: String) {
        Repository.deleteProductAt(productName)
    }

    fun getProductAt(position: Int): Product {
        return Repository.getProductAt(position)
    }

    fun clearList() {
        Repository.clearList()
    }

    fun getList(): MutableList<Product> {
        return Repository.getList()
    }

    fun sortByName() {
        Repository.sortByName()
    }

    fun sortByUnits() {
        Repository.sortByUnits()
    }


}