package com.sing.board4_3.MainFragment

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
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.FragmentNickBinding

import okhttp3.FormBody

import java.util.regex.Pattern
import kotlin.concurrent.thread

class JoinFragment : Fragment() {

    // Binding 설정
    lateinit var nickFragmentBinding: FragmentNickBinding

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

        /**
         *
         */
        nickFragmentBinding.nickJoin.setOnClickListener {

            val nickNameNickName = nickFragmentBinding.nickNickname.text.toString()

            /** 닉네임 유효성 검사 ------
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

                    return@setOnClickListener
                }
            }


            val act = activity as MainActivity
            act.userNick = nickNameNickName


            thread {

                val builderDup = FormBody.Builder()
                builderDup.add("userNick", act.userNick)

                /**
                 * JoinController
                 */
                val dupResponse = UseOkHttp().useThread("join/nickDuplicate",builderDup)


                val result = dupResponse.body?.string()!!.trim()


                /** 정상적으로 통신 완료 ----------------------------------------------------------------
                 */
                if ( dupResponse.isSuccessful )
                {

                    if(result.equals("N"))
                    {
                        activity?.runOnUiThread {

                            DialogEx().makeDialog(
                                requireContext(),
                                "Nick Duplicate",
                                "This nick is being used by someone",
                                "confirm")

                        }
                    }
                    else
                    {//---
                        val builder1 = FormBody.Builder()
                        builder1.add("userId", act.userId)
                        builder1.add("userPw", act.userPw)
                        builder1.add("userNick", act.userNick)
                        builder1.add("userEmail",act.email)

                        /**
                         * JoinController
                         */
                        val response = UseOkHttp().useThread("join",builder1)

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
                }

                /** 통신 오류 ------------------------------------------------------------------------
                 */
                else
                {
                    activity?.runOnUiThread {

                        DialogEx().netWork(requireContext())
                    }

                }

            }



        }
        return nickFragmentBinding.root
    }

}