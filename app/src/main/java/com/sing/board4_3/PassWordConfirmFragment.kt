package com.sing.board4_3

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.sing.board4_3.databinding.FragmentPassWordConfirmBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread

class PassWordConfirmFragment : Fragment() {

    //ViewBinding
    lateinit var passWordConfirmFragmentBinding : FragmentPassWordConfirmBinding

    lateinit var resignOrChange:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val act = activity as BoardMainActivity

        resignOrChange = act.resignOrChange

        passWordConfirmFragmentBinding = FragmentPassWordConfirmBinding.inflate(layoutInflater)

        if(resignOrChange.equals("change"))
        {
            passWordConfirmFragmentBinding.PasswordConfirmToolbar.title =
                "Please enter your password for change"
        }
        else
        {
            passWordConfirmFragmentBinding.PasswordConfirmToolbar.title =
                "Please enter your password for resign"
        }

        val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

        // 비밀번호 확인을 위해서 현재 로그인한 user_idx 를 가져온다
        val userIdx = pref.getInt("login_user_idx",-1)

        // 버튼을 누르면 login_user_idx 를 사용해서 비밀번호가 맞는지 확인하고 다른 fragment 로 이동하게 설정
        passWordConfirmFragmentBinding.passwordConfirmBtn.setOnClickListener {

            // 입력한 비밀번호를 받는다.
            val getPassword = passWordConfirmFragmentBinding.PasswordConfirmPassword.text.toString()

            // 비밀번호 유효성 검
            if(!Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",getPassword))
            {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (getPassword == null || getPassword.length == 0) {
                    dialogBuilder.setTitle("Password Error!")
                    dialogBuilder.setMessage("Please enter your Password")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        passWordConfirmFragmentBinding.PasswordConfirmPassword.requestFocus()
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
                        passWordConfirmFragmentBinding.PasswordConfirmPassword.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }


            }



            // 입력한 비밀번호 값은 이상이 없음으로사 Thread 실행
            thread{
                val client = OkHttpClient()

                val site = "http://${ServerIP.serverIp}/login/getPassword"

                val builder1 = FormBody.Builder()
                builder1.add("user_idx", userIdx.toString())
                builder1.add("get_password",getPassword)

                val Form = builder1.build()

                val request = Request.Builder().url(site).post(Form).build()

                val response = client.newCall(request).execute()

                if(response.isSuccessful)
                {
                    val passwordResult = response.body?.string()!!.trim()

                    response.body?.close()

                    // 비밀번호가 틀렸다면
                    if(passwordResult.equals("Wrong Password"))
                    {
                        activity?.runOnUiThread{
                            val dialogBuilder = AlertDialog.Builder(requireContext())
                            dialogBuilder.setTitle("Wrong Password")
                            dialogBuilder.setPositiveButton("confirm"){
                                    dialogInterface: DialogInterface, i: Int ->
                                passWordConfirmFragmentBinding.PasswordConfirmPassword.setText("")
                                passWordConfirmFragmentBinding.PasswordConfirmPassword.requestFocus()
                            }
                            dialogBuilder.show()
                        }
                    }
                    //비밀번호가 맞다면
                    else
                    {

                        if( resignOrChange=="change" )
                        {
                            Log.i("resignOrChange ",resignOrChange)
                            act.fragmentController("change_password", true, true)
                        }
                        else
                        {
                            act.fragmentController("resign", true, true)
                            act.fragmentRemoveBackStack("password_confirm")
                        }
                    }
                }
            }
        }
        return passWordConfirmFragmentBinding.root
    }

}