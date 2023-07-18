package com.sing.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = mutableListOf<String>()

        items.add("A")
        items.add("B")
        items.add("C")
        items.add("D")
        items.add("E")
        items.add("F")
        items.add("G")
        items.add("1")
        items.add("2")
        items.add("3")
        items.add("4")
        items.add("5")
        items.add("6")
        items.add("7")

        /** RecyclerView 호출
         */
        val recyclerViewVariable = findViewById<RecyclerView>(R.id.recyclerViewId)

        /** Adapter 호출
         */
        val recyclerViewAdapter = RecyclerViewAdapter(items)

        /** RecyclerView 와 Adapter를 연결
         */
        recyclerViewVariable.adapter=recyclerViewAdapter

        /** 내부에서 배치되는 형태를 관리
         */
        recyclerViewVariable.layoutManager = LinearLayoutManager(this)

        /** Click Event 처리
         */
        recyclerViewAdapter.itemClick = object :RecyclerViewAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                // 확인용 Toast Message
                Toast.makeText(baseContext, items[position], Toast.LENGTH_SHORT).show()
            }
        }

    }

}