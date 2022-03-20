package com.sing.mangoPlate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(val context:Context ,val List: MutableList<GetData>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_items, parent, false)

        return ViewHolder(v)
    }

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        if(itemClick !=null)
        {
            holder?.itemView.setOnClickListener { v ->
                itemClick!!.onClick(v, position)
            }
        }
        holder.bindItems(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }
    // ViewHolder 생성
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(item: GetData){

            // Image 불러오기
            val rv_img = itemView.findViewById<ImageView>(R.id.rv_imageArea)

            // Text 불러오기
            val rv_text = itemView.findViewById<TextView>(R.id.rv_textArea)


            rv_text.text = item.titleText

            // rv_img 에 Web 에서 가져온 image 사용하기
            // context --> Activity 의 LifeCycle 을 사용할 수 있다 .
            Glide.with(context).load(item.titleImage).into(rv_img)


        }
    }
}