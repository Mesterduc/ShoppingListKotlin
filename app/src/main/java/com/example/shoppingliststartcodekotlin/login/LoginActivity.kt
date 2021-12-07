package com.example.shoppingliststartcodekotlin.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.databinding.LoginMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: LoginMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
//        binding = LoginMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginButton.setOnClickListener {
            var email = binding.loginEmail.text.toString()
            var password = binding.loginPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
//                        Toast.makeText(
//                            this, "Welcome ${user?. .uid.toString()}",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()

    }

}