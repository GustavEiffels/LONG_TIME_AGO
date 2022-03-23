package com.sing.dating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sing.dating.slider.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter: CardStackAdapter

    //view 를 어떻게 보일 것인지 판단
    lateinit var manager : CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object :CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
                
            }

            override fun onCardSwiped(direction: Direction?) {

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

        val testList = mutableListOf<String>()
        testList.add("I")
        testList.add("Have to")
        testList.add("sleep now")

        // CardStackAdapter 정의
        cardStackAdapter = CardStackAdapter(baseContext,testList)

        // cardStackview 의 adpater 에 해당 어댑터를 연결
        cardStackView.adapter = cardStackAdapter
    }
}