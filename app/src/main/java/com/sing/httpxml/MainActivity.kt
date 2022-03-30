package com.sing.httpxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Element
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xml_btn.setOnClickListener {
            // Thread 구동
            thread{
                // 접속할 page 의 주소
                val site = "http://192.168.0.17:8080/xml.xml"

                //url 객체 생성
                val url = URL(site)

                // connection
                val conn = url.openConnection() as HttpURLConnection

                // server 에서 데이터 받아오기 -- DOM 방식으로
                // XML 문서를 분석할 수 있는 객체를 생성
                val factory = DocumentBuilderFactory.newInstance()
                val builder = factory.newDocumentBuilder()
                val doc = builder.parse(conn.inputStream)


                // 최상위 태그 얻어오기
                val root = doc.documentElement

                // item tag 들을 가져오기
                val item_list = root.getElementsByTagName("item")

                runOnUiThread{
                    textView_scroll.text=""

                    for(i in 0 until item_list.length)
                    {
                        // i  번째 item tag 객체 추출
                        val item_tag = item_list.item(i) as Element


                        // item tag 안의 tag 들을 가져온다.
                        val data1_list = item_tag.getElementsByTagName("data1")
                        val data2_list = item_tag.getElementsByTagName("data2")
                        val data3_list = item_tag.getElementsByTagName("data3")

                        // 0 번째 tag 가져오기
                        val data1_tag =data1_list.item(0) as Element
                        val data2_tag =data2_list.item(0) as Element
                        val data3_tag =data3_list.item(0) as Element

                        // tag 내의 문자열 데이터 가져오기
                        val data1 = data1_tag.textContent
                        val data2 = data2_tag.textContent
                        val data3 = data3_tag.textContent

                        // 데이터 형변환
                        val a2 = data2.toInt()
                        val a3 = data3.toDouble()
                        runOnUiThread {
                            textView_scroll.append("data1 : $data1\n")
                            textView_scroll.append("data2 : $a2\n")
                            textView_scroll.append("data2 : $a3\n")
                    }

                    }
                }
            }

        }
    }
}