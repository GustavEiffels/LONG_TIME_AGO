package com.sing.board4_3.BoardFragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.Support.ServerIP
import com.sing.board4_3.databinding.FragmentChangePasswordBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread

class PwChangeFragment : Fragment() {

    // ViewBinding
    lateinit var changePasswordFragmentBinding: FragmentChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        changePasswordFragmentBinding = FragmentChangePasswordBinding.inflate(layoutInflater)

        // tool bar title 설정
        changePasswordFragmentBinding.changePasswordToolbar.title = "Change Password"


        // Button 누르면 비밀번호가 유효한지 확인하고
        // 유효하다면 비밀번호 db 에 변경하고
        // 현재 activity 종료하면서 다시 로그인 해라고 하기
        changePasswordFragmentBinding.passwordChangeBtn.setOnClickListener {

            // 입력한 비밀번호 값 받기
            val newPw = changePasswordFragmentBinding.changeChangePassword.text.toString()
            val checkPw = changePasswordFragmentBinding.changeCheckPassword.text.toString()

            if(!Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",newPw))
            {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (newPw == null || newPw.length == 0) {
                    dialogBuilder.setTitle("Password Error!")
                    dialogBuilder.setMessage("Please enter your Password")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        changePasswordFragmentBinding.changeChangePassword.requestFocus()
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
                        changePasswordFragmentBinding.changeChangePassword.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }
            }
            if( !newPw.equals(checkPw) )
            {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("Password Error!")
                dialogBuilder.setMessage("passwords do not match")
                dialogBuilder.setPositiveButton("Confirm")
                { dialogInterface: DialogInterface, i: Int ->
                    changePasswordFragmentBinding.changeChangePassword.requestFocus()
                }
                dialogBuilder.show()

                // lambda
                return@setOnClickListener
            }

            // 비밀번호 입력에 이상이 없는 경우
            thread {
                val client = OkHttpClient()
                val site = "http://${ServerIP.serverIp}/modify/changePw"

                val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

                // 비밀번호 확인을 위해서 현재 로그인한 user_idx 를 가져온다
                val userIdx = pref.getInt("login_user_idx",-1)

                // 해당 url 로 새로운 비밀번호와, 현재 사용하고 있는 사람의 index 를 전달한다.
                val builder1 = FormBody.Builder()
                builder1.add("newPw",newPw)
                builder1.add("user_idx",userIdx.toString())

                val form = builder1.build()

                val request = Request.Builder().url(site).patch(form).build()

                val response = client.newCall(request).execute()

                if(response.isSuccessful)
                {
                    activity?.runOnUiThread{
                        Toast.makeText(requireContext(),"Password Changed!! Please Login again", Toast.LENGTH_SHORT).show()
                    }

                    val mainActivity = Intent(requireContext(), MainActivity::class.java)
                    startActivity(mainActivity)
                    activity?.finish()
                }
                else
                {
                    activity?.runOnUiThread{
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("Net Work Error")
                        dialogBuilder.setPositiveButton("confirm",null)
                        dialogBuilder.show()
                    }
                }

            }

        }
        return changePasswordFragmentBinding.root
    }


}