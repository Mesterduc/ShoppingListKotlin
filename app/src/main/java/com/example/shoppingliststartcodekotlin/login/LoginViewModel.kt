package com.example.shoppingliststartcodekotlin.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository

class LoginViewModel : ViewModel() {
    //    val currentName: MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }
//
//    fun getCurrentUserName(userId: String) {
//        val name = Repository.getCurrentUserName(userId).toString()
//        Log.d("TAG", name)
//        currentName.value = Repository.getCurrentUserName(userId).toString()
//    }
    fun getData() {
        Repository.getData()
    }

}