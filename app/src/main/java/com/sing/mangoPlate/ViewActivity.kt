package com.sing.mangoPlate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ViewActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        auth =Firebase.auth

        // WeB View 설정

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)



        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl(intent.getStringExtra("url").toString())


        // Write a message to the database
        val database = Firebase.database
        val bookMark = database.getReference("bookMark")

        val url = intent.getStringExtra("url").toString()
        val imageUrl = intent.getStringExtra("ImageUrl").toString()
        val title = intent.getStringExtra("title").toString()

        val saveText = findViewById<TextView>(R.id.saveText)

        saveText.setOnClickListener{
            // uid 값을 저장하기
            bookMark.child(auth.currentUser!!.uid).push().setValue(GetData(url,imageUrl,title))
            Toast.makeText(this,"URL SAVE COMPLETE", Toast.LENGTH_SHORT).show()


        }
    }
}