package com.sing.dice_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sing.dice_app.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.diceChangeButton.setOnClickListener{

            // log 로 확인
            Log.d("DiceTest", Random.nextInt(1, 6).toString())
            Log.d("DiceTest", Random.nextInt(1, 6).toString())
            println(Random.nextInt(1, 6))


            val diceImage1 = binding.dice1
            val diceImage2 = binding.dice2


            // 버튼을 누르면 number 가 Random 으로 들어가게 하기
            val number1  = Random.nextInt(1, 6)
            val number2  = Random.nextInt(1, 6)

            if(number1 == 1){
                diceImage1.setImageResource(R.drawable.dice_1)
            }else if(number1 == 2){
                diceImage1.setImageResource(R.drawable.dice_2)
            }else if(number1 == 3){
                diceImage1.setImageResource(R.drawable.dice_3)
            }else if(number1 == 4){
                diceImage1.setImageResource(R.drawable.dice_4)
            }else if(number1 == 5){
                diceImage1.setImageResource(R.drawable.dice_5)
            }else{
                diceImage1.setImageResource(R.drawable.dice_6)
            }

            if(number1 == 2){
                diceImage2.setImageResource(R.drawable.dice_1)
            }else if(number1 == 2){
                diceImage2.setImageResource(R.drawable.dice_2)
            }else if(number2 == 3){
                diceImage2.setImageResource(R.drawable.dice_3)
            }else if(number2 == 4){
                diceImage2.setImageResource(R.drawable.dice_4)
            }else if(number2 == 5){
                diceImage2.setImageResource(R.drawable.dice_5)
            }else{
                diceImage2.setImageResource(R.drawable.dice_6)
            }

            val result = number1 + number2



            Toast.makeText(this, "result is " + result.toString(), Toast.LENGTH_LONG).show()



            Random.nextInt(1, 6)

        }






    }
}