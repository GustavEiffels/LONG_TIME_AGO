package com.sing.dating.slider

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sing.dating.R
import com.sing.dating.auth.UserInfoDataSet


class CardStackAdapter(val context: Context, val items : List<UserInfoDataSet>): RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val image = itemView.findViewById<ImageView>(R.id.profileImageArea)

        val nick = itemView.findViewById<TextView>(R.id.cardText_name)

        val age = itemView.findViewById<TextView>(R.id.cardText_age)

        val location = itemView.findViewById<TextView>(R.id.cardText_location)

        fun binding(data : UserInfoDataSet){
            // Storage 에서 image 주소 불러오기
            val storageRef = Firebase.storage.reference.child(data.uid+".png")
            storageRef.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                if(task.isSuccessful)
                {
                    Glide.with(context).load(task.result).into(image)

                }
            })

            nick.text = data.nickname

            age.text = data.age

            location.text = data.location


        }
    }
}