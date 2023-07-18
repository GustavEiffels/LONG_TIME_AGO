package com.sing.mangoPlate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)


        auth  = Firebase.auth


        val joinBtn = findViewById<Button>(R.id.joinBtn)
        // join Button 을 누를 때 작용 설정

        val email = findViewById<EditText>(R.id.joinEmailArea)

        val password = findViewById<EditText>(R.id.joinPasswordArea)


        joinBtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Log.w("joinActivity","Create Account is false ")

                    }
                }

        }

    }
}