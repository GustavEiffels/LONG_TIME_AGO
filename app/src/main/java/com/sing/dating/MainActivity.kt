package com.sing.dating

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sing.dating.auth.IntroActivity
import com.sing.dating.auth.UserInfoDataSet
import com.sing.dating.setting.SettingActivity
import com.sing.dating.setting.UserPageActivity
import com.sing.dating.slider.CardStackAdapter
import com.sing.dating.utils.FirebaseAuthUtils
import com.sing.dating.utils.FirebaseRef
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter: CardStackAdapter

    //view 를 어떻게 보일 것인지 판단
    lateinit var manager : CardStackLayoutManager


    private val userDataList = mutableListOf<UserInfoDataSet>()

    //Count 하기 위한 Variable
    private var userCount = 0

    private lateinit var currentUserLocation: String

    // user uid 값
    private var uid= FirebaseAuthUtils.getUid()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val setting = findViewById<ImageView>(R.id.setting_icon)
        setting.setOnClickListener {


            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)

        }



        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object :CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
                
            }

            override fun onCardSwiped(direction: Direction?) {

                if(direction!!.equals(Direction.Right))
                {
                    Toast.makeText(this@MainActivity,"right",Toast.LENGTH_SHORT).show()
                    Log.i(TAG, userDataList[userCount].uid.toString())
                    userLikeInfo(uid,userDataList[userCount].uid.toString())
                }
                else
                {
                    Toast.makeText(this@MainActivity,"Left",Toast.LENGTH_SHORT).show()
                }

                userCount = userCount+1

                /** 모든 user card 를 전부 다 넘긴 경우 --- -
                 */
                if(userCount == userDataList.count())
                {
                    getUserDataList(currentUserLocation)
                    Toast.makeText(this@MainActivity,"User List Updating",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })

        // cardStackView 의 layoutManager 에 manager 를 연결
        cardStackView.layoutManager = manager


        // CardStackAdapter 정의
        cardStackAdapter = CardStackAdapter(baseContext,userDataList)

        // cardStackview 의 adpater 에 해당 어댑터를 연결
        cardStackView.adapter = cardStackAdapter

//        getUserDataList()
        getMyUserLocation()



    }

    private fun getUserDataList(currentUserLocation : String){
        // Read from the database
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(datas in dataSnapshot.children)
                {
                    Log.d(TAG,datas.toString())

                    val userinfo = datas.getValue(UserInfoDataSet::class.java)

                    if(!userinfo!!.uid.toString().equals(uid))
                    {
                        if(userinfo!!.location.toString().equals(currentUserLocation))
                        {

                            userDataList.add(userinfo!!)
                            Log.i("UserNickName", userinfo?.nickname.toString())
                        }

                    }


                }
                cardStackAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.i("TEST","TESTing")
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.User.addValueEventListener(postListener)
    }

    /** user 정보를 가져오는 method 생성
     */
    private fun getMyUserLocation()
    {
            val postListener = object : ValueEventListener
            {
                override fun onDataChange(dataSnapshot: DataSnapshot)
                {
                    Log.i(TAG,dataSnapshot.toString())
                    val data = dataSnapshot.getValue(UserInfoDataSet::class.java)

                    // 사용하고 있는 user의 location 을 가져오기
                    Log.i(TAG, data?.location.toString())

                    currentUserLocation = data?.location.toString()

                    // 현재 user 의 location 정보를 같이 삽입해준다
                    getUserDataList(currentUserLocation)
                }
                override fun onCancelled(databaseError: DatabaseError)
                {
                    Log.w(TAG, "loadPost:onCanceled",databaseError.toException())
                }
            }
            FirebaseRef.User.child(uid).addValueEventListener(postListener)
        }


    /** 누구한테 좋아요를 받았는지 확인하는 method 만들기
     *  나의 uid, 나를 좋아요한 사람의 uid 값을 얻어야한다.
     */
    private fun userLikeInfo(myUid: String , othersUid: String)
    {
        FirebaseRef.userLike.child(uid).child(othersUid).setValue("who I like")
    }
}