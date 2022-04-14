package com.sing.board4_3

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.sing.board4_3.databinding.FragmentLoginBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread

class LoginFragment : Fragment() {

    // Binding 을 설정
    lateinit var loginFragmentBinding: FragmentLoginBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment

        //Binding
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater)

        loginFragmentBinding.loginToolbar.title="LOGIN"

        loginFragmentBinding.loginJoinBtn.setOnClickListener {

            // 해당 Fragment 를 소유하고 있는 Activity 를 추출할 수 있다
            val act = activity as MainActivity
            act.fragmentController("join",true,true)
        }


        loginFragmentBinding.loginLoginBtn.setOnClickListener {


            // login
            val loginId = loginFragmentBinding.loginId.text.toString()

            // loginPw
            val loginPw = loginFragmentBinding.loginPw.text.toString()


            // 자동 login에 체크가 되었는지 확인
            val loginCheck = loginFragmentBinding.loginAutoLogin.isChecked


            var loginAutoLogin = 0

            if(loginCheck)
            {
                loginAutoLogin = 1
            }


            /**
             *
             *  유효성 체크 ---------------------------------------------------------------
             */
            if(!Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$",loginId))
            {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (loginId == null || loginId.length == 0) {
                    dialogBuilder.setTitle("ID Error!")
                    dialogBuilder.setMessage("Please enter your ID")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        loginFragmentBinding.loginId.requestFocus()
                    }
                    dialogBuilder.show()
                    return@setOnClickListener
                }
                else
                {
                    dialogBuilder.setTitle("ID Error!")
                    dialogBuilder.setMessage("Id Must be 6 to 11 characters in Alphabet and number.")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        loginFragmentBinding.loginId.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }
            }
            /** 비밀번호 유효성 설정하기
             */
            if(!Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",loginPw))
            {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (loginPw == null || loginPw.length == 0) {
                    dialogBuilder.setTitle("Password Error!")
                    dialogBuilder.setMessage("Please enter your Password")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        loginFragmentBinding.loginPw.requestFocus()
                    }
                    dialogBuilder.show()
                    return@setOnClickListener
                }
                else
                {
                    dialogBuilder.setTitle("Password Error!")
                    dialogBuilder.setMessage("8~16 upper, lowercase letters, special characters")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        loginFragmentBinding.loginPw.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }
            }

            // TEST
            Log.d("test",loginId)
            Log.d("test",loginPw)
            Log.d("test","$loginAutoLogin")


            thread{
                val client =OkHttpClient()


                val site = "http://${ServerIP.serverIp}/login"

                val builder1 = FormBody.Builder();

                builder1.add("userId",loginId)
                builder1.add("userPw",loginPw)
                builder1.add("user_autologin", "$loginAutoLogin")

                val formBody = builder1.build()

                val request = Request.Builder().url(site).post(formBody).build()

                val response = client.newCall(request).execute()

                /** 결과 추출
                 */
                if(response.isSuccessful)
                {
                    val result_text = response.body?.string()!!.trim()
                    Log.d("test",result_text)

                    var userNick = ""

                    activity?.runOnUiThread{
                        if(result_text.equals("password is different") || result_text.equals("Not Exist Account"))
                        {
                            val dialogBuilder = AlertDialog.Builder(requireContext())
                            dialogBuilder.setTitle("LoginFail")
                            dialogBuilder.setMessage("$result_text")
                            dialogBuilder.setPositiveButton("confirm")
                            { dialogInterface: DialogInterface, i: Int ->
                                loginFragmentBinding.loginId.setText("")
                                loginFragmentBinding.loginPw.setText("")
                                loginFragmentBinding.loginAutoLogin.isChecked = false
                                loginFragmentBinding.loginId.requestFocus()
                            }
                            dialogBuilder.show()
                        }
                        else
                        {
                            activity?.runOnUiThread{
                                Toast.makeText(requireContext(), "LoginSuccess", Toast.LENGTH_SHORT).show()

                                thread{
                                    val client = OkHttpClient()
                                    val site = "http://${ServerIP.serverIp}/login/getUserNick"
                                    val builder1 = FormBody.Builder()
                                    builder1.add("user_idx",result_text)
                                    val formBody = builder1.build()
                                    val request = Request.Builder().url(site).post(formBody).build()
                                    val response = client.newCall(request).execute()



                                    if(response.isSuccessful)
                                    {
                                        userNick= response.body?.string()!!.trim()
                                        // 사용자 정보 preferences 에 저장
                                        val pref = activity?.getSharedPreferences("login_data", Context.MODE_PRIVATE)

                                        val editor = pref?.edit()

                                        editor?.putInt("login_user_idx",Integer.parseInt(result_text))
                                        editor?.putInt("login_auto_login",loginAutoLogin)
                                        editor?.putString("login_user_nick",userNick)
                                        editor?.commit()
                                        Log.i("Login_user_nick", userNick)
                                        Log.i("login_user_idx","${Integer.parseInt(result_text)}")


                                        val boardMainIntent = Intent(requireContext(), BoardMainActivity::class.java)
                                        startActivity(boardMainIntent)
                                        activity?.finish()

                                    }
                                    else
                                    {
                                        activity?.runOnUiThread{
                                            val dialogBuilder = AlertDialog.Builder(requireContext())
                                            dialogBuilder.setTitle("Net Work Erorr")
                                            dialogBuilder.setPositiveButton("confirm",null)
                                            dialogBuilder.show()
                                        }
                                    }
                                }
                            }
                        }
                    }


                }
                /**
                 *  통신이 좋지 않아 실패했을 때
                 */
                else
                {
                    activity?.runOnUiThread{
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("NetWork Error")
                        dialogBuilder.setMessage("It's something Wrong Try Again")
                        dialogBuilder.setPositiveButton("confirm",null)
                        dialogBuilder.show()
                    }
                }
            }
        }
        return loginFragmentBinding.root
    }

}