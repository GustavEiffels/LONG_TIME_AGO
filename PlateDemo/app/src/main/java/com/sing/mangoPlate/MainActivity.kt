package com.sing.mangoPlate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // GetData 가 들어갈 list 를 생성
    private val items = mutableListOf<GetData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bookMarkButton = findViewById<TextView>(R.id.bookMarkBtn)
        bookMarkButton.setOnClickListener {

            val intent = Intent(this,BookMarkActivity::class.java)
            startActivity(intent)
        }

        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/56MKQk5Vm_",
                "https://mp-seoul-image-production-s3.mangoplate.com/104601_1503734240862785.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "두만강샤브샤브"
            )
        )
        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/3A0Z-gBcPl",
                "https://mp-seoul-image-production-s3.mangoplate.com/47875_1623278961956296.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "로향양꼬치"
            )
        )
        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/y4pGEk_ZwDqZ",
                "https://mp-seoul-image-production-s3.mangoplate.com/222991_1511866490383465.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "퍼블리코타코"
            )
        )
        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/XRoMziImmYCC",
                "https://mp-seoul-image-production-s3.mangoplate.com/331247/60039_1596540913676_34054?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "뉴욕택시디저트"
            )
        )

        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/56MKQk5Vm_",
                "https://mp-seoul-image-production-s3.mangoplate.com/104601_1503734240862785.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "두만강샤브샤브"
            )
        )
        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/3A0Z-gBcPl",
                "https://mp-seoul-image-production-s3.mangoplate.com/47875_1623278961956296.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "로향양꼬치"
            )
        )
        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/y4pGEk_ZwDqZ",
                "https://mp-seoul-image-production-s3.mangoplate.com/222991_1511866490383465.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "퍼블리코타코"
            )
        )
        items.add(
            GetData(
                "https://www.mangoplate.com/restaurants/XRoMziImmYCC",
                "https://mp-seoul-image-production-s3.mangoplate.com/331247/60039_1596540913676_34054?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                "뉴욕택시디저트"
            )
        )


        // ReCyclerView 연결
        val recyclerview = findViewById<RecyclerView>(R.id.mainRv)

        // adapter 호출
        val recyclerview_Adapter = RecyclerViewAdapter(baseContext,items)

        // Adapter 연결
        recyclerview.adapter = recyclerview_Adapter

        // RecyclerView Click Event처리
        recyclerview_Adapter.itemClick = object:RecyclerViewAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {

                // itemclick 이 돼면  새로운 Activity 로 넘겨 줄 것이다 .
                val intent = Intent(baseContext, ViewActivity::class.java)
                intent.putExtra("url",items[position].url)
                intent.putExtra("ImageUrl",items[position].titleImage)
                intent.putExtra("title",items[position].titleText)
                startActivity(intent)
            }

        }

        recyclerview.layoutManager = GridLayoutManager(this, 2)
    }
}