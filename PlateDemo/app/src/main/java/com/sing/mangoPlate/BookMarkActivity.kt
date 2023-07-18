package com.sing.mangoPlate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BookMarkActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    private val contentModel = mutableListOf<GetData>()



    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this,"This is BookMark", Toast.LENGTH_SHORT).show()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_mark)

        auth = Firebase.auth

        val database = Firebase.database
        val bookMark = database.getReference("bookMark")


        val recyclerview = findViewById<RecyclerView>(R.id.bookMarkRv)
        val rvAdapter = RecyclerViewAdapter(this, contentModel)
        recyclerview.adapter = rvAdapter

        recyclerview.layoutManager = GridLayoutManager(this, 2)


        bookMark.child(auth.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (getModel in snapshot.children){

                        Log.d("GetData",getModel.toString())
                        contentModel.add(getModel.getValue(GetData::class.java)!!)

                    }
                    // Adapter 를 동기화 시킴
                    rvAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // 실패하는 경우
                    Log.e("BookMark","dbError")
                }

            })


    }
}