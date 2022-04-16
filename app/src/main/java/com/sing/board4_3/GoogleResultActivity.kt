package com.sing.board4_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sing.board4_3.databinding.ActivityGoogleResultBinding

class GoogleResultActivity : AppCompatActivity() {

    lateinit var googleResultActivityBinding : ActivityGoogleResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_result)

        var googleEmail:String?

        googleEmail = intent.getStringExtra("google")

        Log.i("GoogleResultActivity",googleEmail.toString())

        googleResultActivityBinding = ActivityGoogleResultBinding.inflate(layoutInflater)

        setContentView(googleResultActivityBinding.root)

        googleResultActivityBinding.googleIdTest.text=googleEmail.toString()

    }
}