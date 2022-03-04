package com.sing.sana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class sana_Inside : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sana_inside)

        val getData = intent.getStringExtra("data")

        val test = Integer.parseInt(getData)

        println(test)

        Toast.makeText(this, "sana"+getData, Toast.LENGTH_LONG).show()

        val memberImage = findViewById<ImageView>(R.id.commonId)









        if(getData == "1"){
            memberImage.setImageResource(R.drawable.sana01)
        }
        if(getData == "2"){
            memberImage.setImageResource(R.drawable.sana02)
        }
        if(getData == "3"){
            memberImage.setImageResource(R.drawable.sana03)
        }
        if(getData == "4"){
            memberImage.setImageResource(R.drawable.sana04)
        }
        if(getData == "5"){
            memberImage.setImageResource(R.drawable.sana05)
        }
        if(getData == "6"){
            memberImage.setImageResource(R.drawable.sana06)
        }
        if(getData == "7"){
            memberImage.setImageResource(R.drawable.sana07)
        }
        if(getData == "8"){
            memberImage.setImageResource(R.drawable.sana08)
        }
        if(getData == "9"){
            memberImage.setImageResource(R.drawable.sana09)
        }
    }
}