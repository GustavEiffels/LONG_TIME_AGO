package com.sing.httpmedia

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_load_btn.setOnClickListener {


            thread{
                    // URL 을 지정
                    val url = URL("http://172.20.10.6:8080/download.jpg")

                    // connecting
                    val conn = url.openConnection() as HttpURLConnection

                    // Image 는 BitMap 을 사용
                    val bitmap = BitmapFactory.decodeStream(conn.inputStream)

                    runOnUiThread{
                        image_area.setImageBitmap(bitmap)
                    }

            }


        }
        move_to_play_page_btn.setOnClickListener {
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }

        goto_video.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }



    }
}