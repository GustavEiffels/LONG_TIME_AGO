package com.sing.httpnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Http_btn.setOnClickListener {

            thread {
                // 접속할 주소
                val site = "http://172.20.10.6:8080"


                // URL 주소로 설정
                val url = URL(site)


                // Connecting
                val conn = url.openConnection() as HttpURLConnection

                // 데이터 받아오기
                val isr = InputStreamReader(conn.inputStream, "UTF-8")
                val br = BufferedReader(isr)

                // 줄단위로 읽어오기
                var parameter:String? = null

                val buf = StringBuffer()

                do
                {
                    // parameter 를 한번 읽어오기
                 parameter = br.readLine()

                    // parameter 값이 0 이 아닐 경우
                    if(parameter!=null)
                    {
                        // buffer 에 parameter 를 추가한다
                        buf.append("$parameter\n")
                    }
                }
                    while(parameter!=null)

                    runOnUiThread{

                        Http_text.text = buf.toString()
                    }
            }

        }
    }
}