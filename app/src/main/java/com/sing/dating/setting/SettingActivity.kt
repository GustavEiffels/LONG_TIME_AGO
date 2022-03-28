package com.sing.dating.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sing.dating.R
import com.sing.dating.auth.IntroActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        /** user_pageBtn 을 누르면 UserPageActivity 로 이동하게 만듬
         */
        findViewById<Button>(R.id.user_pageBtn).setOnClickListener {
            startActivity(Intent(this, UserPageActivity::class.java))

        }

        findViewById<Button>(R.id.setting_logoutBtn).setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()
            startActivity(Intent(this, IntroActivity::class.java))
        }

    }
}