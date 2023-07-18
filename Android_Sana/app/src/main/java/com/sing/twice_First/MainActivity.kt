package com.sing.twice_First

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1.  화면이 클릭되었다는 것을 인식
        val image1 = findViewById<ImageView>(R.id.sana01)
        image1.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana1::class.java)
            startActivity(intent)
        }

        val image2 = findViewById<ImageView>(R.id.sana02)
        val image3 = findViewById<ImageView>(R.id.sana03)
        val image4 = findViewById<ImageView>(R.id.sana04)
        val image5 = findViewById<ImageView>(R.id.sana05)
        val image6 = findViewById<ImageView>(R.id.sana06)
        val image7 = findViewById<ImageView>(R.id.sana07)
        val image8 = findViewById<ImageView>(R.id.sana08)


        image2.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana2::class.java)
            startActivity(intent)
        }

        image3.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana3::class.java)
            startActivity(intent)
        }
        image4.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana4::class.java)
            startActivity(intent)
        }

        image5.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana5::class.java)
            startActivity(intent)
        }

        image6.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana6::class.java)
            startActivity(intent)
        }

        image7.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana7::class.java)
            startActivity(intent)
        }

        image8.setOnClickListener {

            Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면 다음 화면으로 넘어가서 화면을 크게 보여줌
            val intent = Intent(this, Sana8::class.java)
            startActivity(intent)
        }



    }
}