package com.sing.board4_3.MainFragment

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
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sing.board4_3.databinding.FragmentLoginBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread
import com.sing.board4_3.Activity.BoardMainActivity
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.R
import com.sing.board4_3.Support.ServerIP
import org.json.JSONObject


class LoginFragment : Fragment(){

    // Binding 을 설정
    lateinit var loginFragmentBinding: FragmentLoginBinding

    var auth:FirebaseAuth? = null

    var googleSignInClient : GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9000




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

        val activity = activity as MainActivity



        //Binding
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater)

        loginFragmentBinding.loginToolbar.title="LOGIN"


        auth = FirebaseAuth.getInstance()

        loginFragmentBinding.signInButton.setOnClickListener {
            googleLogin()
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // options 에 google SignIn 에 등록
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)





        loginFragmentBinding.loginJoinBtn.setOnClickListener {

            // 해당 Fragment 를 소유하고 있는 Activity 를 추출할 수 있다
            val act = activity as MainActivity
            act.fragmentController("email",true,true)
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
            /** 비밀번호 유효성 설정하기 ------
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


        /** Find User Account
         */
        loginFragmentBinding.findAccount.setOnClickListener {
         activity.fragmentController("find_account",true,true)
        }

        /** Reset User Password
         */
        loginFragmentBinding.resetPassword.setOnClickListener {
            activity.fragmentController("reset_password",true, true)

        }



        return loginFragmentBinding.root
    }

    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_LOGIN_CODE)
        {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if(result!!.isSuccess)
            {
                var account = result.signInAccount
                firebaseAuthWithGoogle(account)

                var googleEmail = account!!.email.toString()
                Log.i("google Email",googleEmail)

                val act = activity as MainActivity

                act.email = googleEmail

                thread{
                    val client = OkHttpClient()

                    val site = "http://${ServerIP.serverIp}/login/googleCheck"

                    val builder1 = FormBody.Builder()
                    builder1.add("email",googleEmail)


                    Log.i("google Email!!!!!",googleEmail)

                    val form = builder1.build()

                    val request  = Request.Builder().url(site).post(form).build()

                    val response = client.newCall(request).execute()


                    /** response.isSuccessful ----> 통신이 됐을 때
                     */
                    if(response.isSuccessful)
                    {
                        // 닉네임 존재 여부
                        val result = response.body?.string()!!.trim()


                        // 닉네임이 존재하지 않는 경우
                        if( result.equals("Y") )
                        {
                            // 회원가입 하게 만듬
                            act.fragmentController("join",true,true)

                        }
                        else
                        {
                            // 계정이 존재함으로 main page 로 이동
                            thread{

                                val client2 = OkHttpClient()
                                val site = "http://${ServerIP.serverIp}/login/googleAccount"

                                val request1 = Request.Builder().url(site).post(form).build()

                                val response1 = client2.newCall(request1).execute()

                                if(response1.isSuccessful)
                                {
                                    val account = response1.body?.string()!!.trim()

                                    val json = JSONObject(account)

                                    Log.i("LoginFragment Google",json.getString("login_user_nick"))

                                    val pref = activity?.getSharedPreferences("login_data", Context.MODE_PRIVATE)

                                    val editor = pref?.edit()


                                    editor?.putInt("login_user_idx",json.getInt("login_user_idx"))
                                    editor?.putInt("login_auto_login",json.getInt("login_auto_login"))
                                    editor?.putString("login_user_nick",json.getString("login_user_nick"))
                                    editor?.commit()

                                    Log.i("test",json.getString("login_user_nick"))

                                    val boardMainIntent = Intent(requireContext(), BoardMainActivity::class.java)
                                    startActivity(boardMainIntent)
                                    activity?.finish()

                                }
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

                    }
                    /** 통신이 안됐을 때
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
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?)
    {
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
    }


}



