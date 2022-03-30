package com.sing.httpmedia

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_music.*
import java.net.URL

class MusicActivity : AppCompatActivity()
{
    // mediaplayer 객체가 null 인지 확인
    var mp:MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        move_to_main_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 음악 재생 버
        play_btn.setOnClickListener {
            if( mp==null )
            {
                Log.i("music","Start")
                // URL 을 지정
                val uri = Uri.parse("http://172.20.10.6:8080/iu.mp3")

                // media Player 생성
                mp = MediaPlayer.create(this, uri)
                mp?.start()
            }

        }
        // 음악 정지 버튼
        stop_btn.setOnClickListener {
            if(mp!=null)
            {
                Log.i("music","Stop")
                mp?.stop()
                mp = null
            }
        }


    }
}