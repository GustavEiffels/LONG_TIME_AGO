package com.sing.sana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "Click Complete", Toast.LENGTH_LONG).show()


        val btn1 = findViewById<ImageView>(R.id.sana01)
        val btn2 = findViewById<ImageView>(R.id.sana02)
        val btn3 = findViewById<ImageView>(R.id.sana03)

        val btn4 = findViewById<ImageView>(R.id.sana04)
        val btn5 = findViewById<ImageView>(R.id.sana05)
        val btn6 = findViewById<ImageView>(R.id.sana06)

        val btn7 = findViewById<ImageView>(R.id.sana07)
        val btn8 = findViewById<ImageView>(R.id.sana08)
        val btn9 = findViewById<ImageView>(R.id.sana09)

        btn1.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            // 데이터를 이동할 때 1 이라는 밗을 보내준다
            intent.putExtra("data","1")
            startActivity(intent)

        }
        btn2.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","2")
            startActivity(intent)

        }
        btn3.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","3")
            startActivity(intent)

        }


        btn4.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","4")
            startActivity(intent)

        }
        btn5.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","5")
            startActivity(intent)

        }
        btn6.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","6")
            startActivity(intent)

        }


        btn7.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","7")
            startActivity(intent)

        }
        btn8.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","8")
            startActivity(intent)

        }
        btn9.setOnClickListener{
            val intent = Intent(this, sana_Inside::class.java)
            intent.putExtra("data","9")
            startActivity(intent)

        }
    }
}