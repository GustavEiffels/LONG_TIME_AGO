package com.sing.board4_3

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
import com.sing.board4_3.databinding.FragmentNickBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread

class NickFragment : Fragment() {

    // Binding 설정
    lateinit var nickFragmentBinding:FragmentNickBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        nickFragmentBinding = FragmentNickBinding.inflate(inflater)

        nickFragmentBinding.toolbar.title="Set your Nick Name!"


        nickFragmentBinding.nickJoin.setOnClickListener {

            val nickNameNickName = nickFragmentBinding.nickNickname.text.toString()

            /** 비밀번호 유효성 설정하기
             */
            if (!Pattern.matches("^[가-힣a-zA-Z0-9]{2,20}$", nickNameNickName)) {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (nickNameNickName == null || nickNameNickName.length == 0) {
                    dialogBuilder.setTitle("NickName Error!")
                    dialogBuilder.setMessage("Please enter  NickNmae")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        nickFragmentBinding.nickNickname.requestFocus()
                    }
                    dialogBuilder.show()
                    return@setOnClickListener
                } else {
                    dialogBuilder.setTitle("Nick Error!")
                    dialogBuilder.setMessage("2-20 characters in Korean, English, numbers")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        nickFragmentBinding.nickNickname.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }
            }


            val act = activity as MainActivity
            act.userNick = nickNameNickName


            /** Thread
             * */
            thread {


                /** nick Duplicate ---- 먼저 nick name 중복성 검사부터 실시한다
                 *  그렇지 않으면 회원 가입 부터 되고 nick name 중복검사를 진행하기 때문에
                 *  중복성에 문제가 생긴다.
                 */
                val nickDupUrl = "http://${ServerIP.serverIp}/nickDuplicate"

                val nickDupClient = OkHttpClient()

                val builderDup = FormBody.Builder()
                builderDup.add("userNick", act.userNick)

                val dupRequest = Request.Builder().url(nickDupUrl).post(builderDup.build()).build()

                val dupResponse = nickDupClient.newCall(dupRequest).execute()


                val result_text = dupResponse.body?.string()!!.trim()


                /**
                 */
                if ( dupResponse.isSuccessful && result_text.equals("This nick is being used by someone") )
                {

                    activity?.runOnUiThread {

                            val dialogBuilder = AlertDialog.Builder(requireContext())
                            dialogBuilder.setTitle("Nick Duplicate")
                            dialogBuilder.setMessage("$result_text")
                            dialogBuilder.setPositiveButton("confirm", null)
                            dialogBuilder.show()
                        }

                }
                else if(dupResponse.isSuccessful && !result_text.equals("This nick is being used by someone"))
                {
                    val client = OkHttpClient()

                    val url = "http://${ServerIP.serverIp}/save"

                    val builder1 = FormBody.Builder()
                    builder1.add("userId", act.userId)
                    builder1.add("userPw", act.userPw)
                    builder1.add("userNick", act.userNick)

                    val formBody = builder1.build()

                    val request = Request.Builder().url(url).post(formBody).build()


                    val response = client.newCall(request).execute()

                    if (response.isSuccessful)
                    {

                        activity?.runOnUiThread{
                            Toast.makeText(
                                requireContext(),
                                "Welcome to join us",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        val mainIntent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(mainIntent)
                        activity?.finish()

                    }
                }

                else
                {
                    // 통신 이상 시
                    activity?.runOnUiThread {

                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("JOIN ALARM")
                        dialogBuilder.setMessage("Something is Wrong ")
                        dialogBuilder.setPositiveButton("confirm", null)
                        dialogBuilder.show()

                    }
                }
            }
        }
        return nickFragmentBinding.root
    }

}