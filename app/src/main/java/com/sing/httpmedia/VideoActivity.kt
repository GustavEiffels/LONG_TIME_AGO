package com.sing.httpmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_video.*
import kotlin.concurrent.thread

class VideoActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)


        move_to_main_btn2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        vplay_btn.setOnClickListener {
                // 영상이 재생중이 아니라면
                if( video_area.isPlaying == false )
                {
                    // uri 지정
                    val uri = Uri.parse("http://172.20.10.6:8080/video.mp4")

                    // uri 연결
                    video_area.setVideoURI(uri)
                    video_area.start()

            }
        }
        vstop_btn.setOnClickListener {
            // 영상이 작동 중이라면
            if( video_area.isPlaying )
            {
                video_area.stopPlayback()
            }
        }
    }
}