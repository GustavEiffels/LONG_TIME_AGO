package com.sing.dating.setting

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sing.dating.R
import com.sing.dating.auth.UserInfoDataSet
import com.sing.dating.utils.FirebaseAuthUtils
import com.sing.dating.utils.FirebaseRef

class UserPageActivity : AppCompatActivity()
{

    private val uid = FirebaseAuthUtils.getUid()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)

        getMyData()
    }


    private fun getMyData()
    {
        val user_profile = findViewById<ImageView>(R.id.setting_profile)
        val user_uid = findViewById<TextView>(R.id.setting_uid)
        val user_email = findViewById<TextView>(R.id.setting_email)
        val user_nick = findViewById<TextView>(R.id.setting_nickname)
        val user_gender = findViewById<TextView>(R.id.setting_gender)
        val user_location = findViewById<TextView>(R.id.setting_location)
        val user_age = findViewById<TextView>(R.id.setting_age)




        val postListener = object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot)
            {

                Log.i(TAG,dataSnapshot.toString())
                    val data = dataSnapshot.getValue(UserInfoDataSet::class.java)

                user_uid.text = data!!.uid
                user_email.text = data!!.email
                user_nick.text = data!!.nickname
                user_gender.text = data!!.gender
                user_location.text = data!!.location
                user_age.text = data!!.age


                // Image 들고오기

                val strongRef = Firebase.storage.reference.child(data.uid+".png")
                strongRef.downloadUrl.addOnCompleteListener(
                    OnCompleteListener { task->
                    if(task.isSuccessful)
                    {
                        Glide.with(baseContext)
                            .load(task.result)
                            .into(user_profile)
                    }
                })

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCanceled",databaseError.toException())
            }

        }
        FirebaseRef.User.child(uid).addValueEventListener(postListener)
    }


}