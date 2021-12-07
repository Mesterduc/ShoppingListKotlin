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

class LoginActivity : Fragment() {
    lateinit var binding: LoginMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        super.onCreate(savedInstanceState)

        auth = Firebase.auth

//        binding = LoginMainBinding.inflate(layoutInflater)
        val view = binding.root
        val binding =
            inflater.inflate(R.layout.login_main, container, false)
//        setContentView(view)



        return binding
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        binding.buttonHome.setOnClickListener{
//            val action = HomeFragmentDirections.actionNavAccountToNavAbout("hello")
//            findNavController().navigate(action)
//        }
//        val button: Button = root.findViewById(R.id.loginButton)
        binding.loginButton.setOnClickListener {
            var email = binding.loginEmail.text.toString()
            var password = binding.loginPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            activity, "Welcome ${user?.uid.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
//                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            activity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        super.onActivityCreated(savedInstanceState)
    }
}