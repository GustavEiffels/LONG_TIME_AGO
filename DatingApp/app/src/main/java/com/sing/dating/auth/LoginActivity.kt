package com.sing.dating.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sing.dating.MainActivity
import com.sing.dating.R

class LoginActivity : AppCompatActivity()
{

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?)

    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = Firebase.auth

        val loginBtn = findViewById<Button>(R.id.login_loginBtn)
        loginBtn.setOnClickListener {

            val email = findViewById<TextInputEditText>(R.id.login_email_area)
            val pw = findViewById<TextInputEditText>(R.id.login_password_area)

            val userEmail = email.text.toString()

            val userPassword = pw.text.toString()


            auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this)
                { task ->

                    if (task.isSuccessful)
                    {
                        Log.i("userId",userEmail)
                        Log.i("userPassword",userPassword)
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    else
                    {
                        Toast.makeText(this,"Your Account Value", Toast.LENGTH_SHORT).show()
                    }
                }


        }


    }
}