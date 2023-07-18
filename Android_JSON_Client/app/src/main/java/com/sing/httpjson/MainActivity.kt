package com.sing.httpjson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
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

        mainJsonBtn.setOnClickListener {
        //Thread 구현
            thread{
                val site = "http://172.30.1.37:8080/json.json"

                val url = URL(site)

                val conn =url.openConnection() as HttpURLConnection


                // 문자열 데이터 읽어오기
                val isr = InputStreamReader(conn.inputStream, "UTF-8")

                val br = BufferedReader(isr)

                var str:String? = null
                val buf = StringBuffer()

                do
                {
                    str = br.readLine()
                    if( str != null)
                    {
                        buf.append(str)
                    }
                }
                    while ( str!=null )

                    // 읽어온 데이터를 문자열로 변환
                    val data = buf.toString()

                    // JSON데이터 분석

                    // text 비워두기
                    runOnUiThread{
                        textView_scroll.text=""
                    }

                    // JSON 배열 생성
                    val root = JSONArray(data)

                    // 배열의 객체 개수만큼 반복
                    for( i in 0 until root.length())
                    {
                        // i 번째 JSON 데이터 객체를 추출
                        val obj = root.getJSONObject(i)

                        // 데이터 추출
                        val data1 = obj.getString("data1")
                        val data2 = obj.getInt("data2")
                        val data3 = obj.getDouble("data3")

                        runOnUiThread{
                            textView_scroll.append("data1 : $data1\n")
                            textView_scroll.append("data2 : $data2\n")
                            textView_scroll.append("data3 : $data3\n\n")
                        }
                    }

            }
        }


    }
}