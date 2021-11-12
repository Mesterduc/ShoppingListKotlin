package com.example.shoppingliststartcodekotlin

import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository

class MainViewModel: ViewModel() {

    fun getData(): MutableLiveData<MutableList<Product>> {
        return Repository.getData()
    }
    fun add() {
        Log.d("hej","create vm")
        Repository.addProduct("hello")
    }

}