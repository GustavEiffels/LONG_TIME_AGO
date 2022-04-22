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
import java.util.regex.Pattern
import kotlin.concurrent.thread
import com.sing.board4_3.Activity.BoardMainActivity
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.R
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.UseOkHttp
import org.json.JSONObject


class LoginFragment : Fragment(){

    /** ViewBinding */
    lateinit var loginFragmentBinding: FragmentLoginBinding

    /**  FireBaseAuth  */
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

        val activity = activity as MainActivity



        // Binding 초기화
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater)


        // toolbar title setting
        loginFragmentBinding.loginToolbar.title="LOGIN"


        // auth 초기화
        auth = FirebaseAuth.getInstance()


        /** signInButton 누르면 Google Login 이 실행되게 설정 */
        loginFragmentBinding.signInButton.setOnClickListener {
            googleLogin()
        }


        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        /**  options 에 google SignIn 에 등록 */
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


        /** join Button 누르면 Join Fragment 로 이동하도록 설정 */
        loginFragmentBinding.loginJoinBtn.setOnClickListener {

            // 해당 Fragment 를 소유하고 있는 Activity 를 추출할 수 있다
            val act = activity as MainActivity
            act.fragmentController("email",true,true)
        }


        /** Login Button 눌렀을 때 */
        loginFragmentBinding.loginLoginBtn.setOnClickListener {


            // Id 정보
            val loginId = loginFragmentBinding.loginId.text.toString()

            // Password
            val loginPw = loginFragmentBinding.loginPw.text.toString()


            // 자동 로그인에 체크 했는지 확인
            val loginCheck = loginFragmentBinding.loginAutoLogin.isChecked


            var loginAutoLogin = 0

            if(loginCheck)
            {
                loginAutoLogin = 1
            }


            /** Id 유효성 검사 */
            if(!Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$",loginId))
            { //Id Valid Check ------

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
            }// Id Valid Check ------



            /** 비밀번호 유효성 검사 */
            if(!Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",loginPw))
            { // PasswordValidCheck ------

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

            }// PasswordValidCheck ------



            thread{
                // Thread 1 ----------

                val builder1 = FormBody.Builder();
                builder1.add("userId",loginId)
                builder1.add("userPw",loginPw)
                builder1.add("user_autologin", "$loginAutoLogin")

                /** LoginController --- login */
                val response = UseOkHttp().useThread("login",builder1)


                /** Success */
                if(response.isSuccessful)
                {  // login response success ----------

                    val result_text = response.body?.string()!!.trim()

                    var userNick = ""


                    activity?.runOnUiThread{
                        // runOnUiThread 1 ----------

                        /** 비밀번호 입력 오류  */
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

                        /** 로그인 성공 */
                        else
                        { // 로그인 성공 ----------
                            activity?.runOnUiThread{
                                // runOnUiThread ----------

                                Toast.makeText(requireContext(), "LoginSuccess", Toast.LENGTH_SHORT).show()

                                thread{
                                    // thread 2 ------------

                                    val builder1 = FormBody.Builder()
                                    builder1.add("user_idx",result_text)

                                    /** LoginController ----> getUserNick */
                                    val response = UseOkHttp().useThread("login/getUserNick",builder1)


                                    /** Success */
                                    if(response.isSuccessful)
                                    {
                                        userNick= response.body?.string()!!.trim()

                                        // sharedReference 에 User  정보 저장 ---------------
                                        val pref = activity?.getSharedPreferences("login_data", Context.MODE_PRIVATE)

                                        val editor = pref?.edit()

                                        editor?.putInt("login_user_idx",Integer.parseInt(result_text))
                                        editor?.putInt("login_auto_login",loginAutoLogin)
                                        editor?.putString("login_user_nick",userNick)
                                        editor?.commit()



                                        // sharedReference 에 User  정보 저장 ---------------

                                        val boardMainIntent = Intent(requireContext(), BoardMainActivity::class.java)
                                        startActivity(boardMainIntent)
                                        activity?.finish()

                                    }
                                    /** Fail */
                                    else
                                    {
                                        activity?.runOnUiThread{
                                            DialogEx().netWork(requireContext())
                                        }
                                    }


                                }// thread 2 ------------

                            }// runOnUiThread ----------

                        } // 로그인 성공 ----------

                    }// runOnUiThread 1 ----------


                } // login response success ----------

                /** Fail */
                else
                {
                    activity?.runOnUiThread{
                        DialogEx().netWork(requireContext())
                    }
                }

            }// Thread 1 ----------


        }// loginLoginBtn.setOnClickListener -----------



        /** 유저 정보를 찾는 Fragment로 이동  */
        loginFragmentBinding.findAccount.setOnClickListener {
         activity.fragmentController("find_account",true,true)
        }

        /** 유저의 비밀번호를 재설정하는 Fragment로 이동  */
        loginFragmentBinding.resetPassword.setOnClickListener {
            activity.fragmentController("reset_password",true, true)

        }


        return loginFragmentBinding.root
    } /** OnCreateView */



    /** Google Login 을 위한 method */
    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    /** Google Login 값 반환 했을 때 처리하는 method */
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

                // 반환받은 정보에서 email 추출하기
                var googleEmail = account!!.email.toString()



                val act = activity as MainActivity

                // activity 의 Email 에 googleEmail 저장하기
                act.email = googleEmail

                thread{
                    // thread 1 ----------

                    val builder1 = FormBody.Builder()
                    builder1.add("email",googleEmail)

                    /** LoginController ----------- googleCheck */
                    val response = UseOkHttp().useThread("login/googleCheck",builder1)


                    /** Success */
                    if(response.isSuccessful)
                    { // response.isSuccessful ------------

                        // 닉네임 존재 여부
                        val result = response.body?.string()!!.trim()


                        /** 닉네임이 존재하지 않을 때 join Fragment 로 이동 */
                        if( result.equals("NotExist") )
                        {

                            act.fragmentController("join",true,true)

                        }
                        /** 닉네임이 존재한다는 것은 이미 계정이 있다는 뜻임으로 BoardMain으로 이동  */
                        else
                        {
                            // 계정이 존재함으로 main page 로 이동
                            thread{

                                /** LoginController ----- googleAccount */
                                val response1 = UseOkHttp().useThread("login/googleAccount",builder1)

                                if(response1.isSuccessful)
                                {
                                    val account = response1.body?.string()!!.trim()

                                    val json = JSONObject(account)


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
                                        DialogEx().netWork(requireContext())
                                    }
                                }


                            }
                        }

                    }
                    /** Fail */
                    else
                    {
                        activity?.runOnUiThread{
                            DialogEx().netWork(requireContext())
                        }
                    }

                }// thread 1 ----------
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?)
    {
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
    }


}



