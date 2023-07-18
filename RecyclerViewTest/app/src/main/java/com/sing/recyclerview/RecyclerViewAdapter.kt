package com.sing.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter
    (val items: MutableList<String>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()
{
    /** ClickEvent 처리
     */
    interface ItemClick{
        fun onClick(view :View , position: Int)
    }
    var itemClick : ItemClick? = null

    /** ViewHolder 생성
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_items, parent, false)

        return ViewHolder(view)
    }

    /** postion 에 해당하는 값을 Holder에 바인딩
     */
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int)
    {
        // itemClick 이 발생했을 때
        if(itemClick !=null){
            holder.itemView.setOnClickListener {
                v->itemClick?.onClick(v, position)
            }
        }

        holder.bindItems(items[position])
    }

    /** 전체 리사이클러뷰의 개수
     */
    override fun getItemCount(): Int
    {
        return items.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindItems(items : String)
        {
            val recyclerView_text = itemView.findViewById<TextView>(R.id.recyclerViewItem)
            recyclerView_text.text = items
        }
    }
}