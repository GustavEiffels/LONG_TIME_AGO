package com.sing.dating.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sing.dating.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)


        /** login Btn 눌렀을때, LoginActivity 를 실행
         */
        intro_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }



        /** joinBtn 을 누를 때 , JoinActivity 로 이동시키기
         */
        intro_join.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}