package com.sing.wisesaying

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListViewAdapter(val List : MutableList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        // 전체 list의 크기
        return List.size
    }

    override fun getItem(p0: Int): Any {

        return List[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var p1 = p1
        if( p1==null){
            p1 = LayoutInflater.from(p2?.context).inflate(R.layout.listview_item, p2, false)
        }

        val listViewText = p1?.findViewById<TextView>(R.id.listViewTextArea)
        listViewText!!.text = List[p0]

        return p1!!
    }
}