package com.sing.dating

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.sing.dating.auth.IntroActivity
import com.sing.dating.utils.FirebaseAuthUtils

class SplashActivity : AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.i("is Already Login User?", FirebaseAuthUtils.getUid())

        val uid = FirebaseAuthUtils.getUid()

        if(uid == "null")
        {
            /** 추가할 method --- 3초 있다가 IntroActivity 로 이동
             */
            Handler().postDelayed({
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, 3000)

        }
        // uid 가 "null"이 아님으로 로그인 된 계정
        else
        {
            /** MainActivity 로 이동하는 method
             */
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, 3000)
        }


    }
}