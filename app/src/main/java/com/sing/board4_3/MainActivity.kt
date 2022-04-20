package com.sing.board4_3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sing.board4_3.databinding.ActivityMainBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity()
{
    lateinit var mainAcitivityBindg: ActivityMainBinding

    // Fragment 를 담을 변수 생성
    lateinit var currentFragment : Fragment

    var email =""


    /**  회원 가입 처리를 위한 변수 생성
     */
    var userId =""
    var userPw = ""
    var userNick = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        SystemClock.sleep(2000)
        setTheme(R.style.Theme_Board4_3)

        Log.i("Email Main Activity", email)

        mainAcitivityBindg = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainAcitivityBindg.root)

        /**
         * pref 값 가져오기
         */
        val pref = getSharedPreferences("login_data", Context.MODE_PRIVATE)


        /** 저장된 값이 없으면 -1 추출
         * */
        val loginUserIdx = pref.getInt("login_user_idx", -1)

        val loginAutoLogin = pref.getInt("login_auto_login",-1)

        val loginUserNick = pref.getString("login_user_nick","")

        Log.d("저장된 loginAuto 값 들고오기", "$loginAutoLogin")

        if(loginAutoLogin == 1)
        {
            //Server 에서 확인
            thread{

                Log.d("thread Test", "come here")

                val url = "http://${ServerIP.serverIp}/login/getAutoLoginInfo"

                val client = OkHttpClient()
                val builder1 = FormBody.Builder()

                builder1.add("userIdx","$loginUserIdx")

                Log.d("login_user_idx","$loginUserIdx" )

                val request= Request.Builder().url(url).post(builder1.build()).build()


                Log.d("before Response", "------------------ ")

                val response = client.newCall(request).execute()


                Log.d("afterResponse", "------------------ ")
                if(response.isSuccessful)
                {
                    val result_text = response.body?.string()!!.trim()

                    Log.d("result_text","$result_text")

                    val loginCheck = Integer.parseInt(result_text)


                    Log.d("loginCheck","$loginCheck")

                    if(loginCheck == 1)
                    {

                        val boardMainIntent = Intent(this, BoardMainActivity::class.java)
                        startActivity(boardMainIntent)
                        finish()
                    }
                    else
                    {
                        fragmentController("login", false, false)
                    }
                }

            }
        }
        fragmentController("login", false, false)
    }



    // Fragment 관리
    fun fragmentController(name: String, add:Boolean, animate:Boolean)
    {
        when(name)
        {
            "login"->
            {
                currentFragment = LoginFragment()
            }

            "join"->
            {
                currentFragment = JoinFragment()
            }

            "nick"->
            {
                currentFragment = NickFragment()
            }
            "email"->
            {
                currentFragment= EmailFragment()
            }
            "find_account"->
            {
                currentFragment=FindAccountFragment()
            }
        }

        val trans = supportFragmentManager.beginTransaction()

        // main_Container : activity_main.xml 에서 fragment 가 배치될 Layout id
        trans.replace(R.id.main_Container, currentFragment)


        if(add)
        {
            // name = Fragment 이름을 넣어줌
            // name 을 가지고 BackStack 에서 제거 할수 있는 기능구현 가능
            trans.addToBackStack(name)
        }
        if(animate)
        {
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        trans.commit()
    }

    /**
     *  이름을 받아서 Fragment 를 제거하는 method
     */
    fun fragmentRemoveBackStack(name:String)
    {
        // FragmentManager.POP_BACK_STACK_INCLUSIVE --> Fragment 가 제거되게 설정
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}