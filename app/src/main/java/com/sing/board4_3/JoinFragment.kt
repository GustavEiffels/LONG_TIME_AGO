package com.sing.board4_3

import android.content.DialogInterface
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.sing.board4_3.databinding.FragmentJoinBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread


class JoinFragment : Fragment() {

    //Binding 설정

    lateinit var joinFragmentBinding : FragmentJoinBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        joinFragmentBinding = FragmentJoinBinding.inflate(inflater)

        joinFragmentBinding.joinToolbar.title="JOIN"




        joinFragmentBinding.joinNextBtn.setOnClickListener {


            var idDuplicateCheck= false;
            /**
             *  변수 처리하기
             *
             *  text 를 사용해서 가져오면 editable 객체로 가져오기 때문에
             *  toString 해주어야 한다,
             */

            val joinId = joinFragmentBinding.joinId.text.toString()
            val joinPw = joinFragmentBinding.joinPw.text.toString()


            /**
             *  null check
             *
             *  requireContext() ---> null 허용 하지 않음 , 안정성이 있다
             */
            if(!Pattern.matches("^[a-zA-Z]{2}[a-zA-Z0-9_]{4,11}$",joinId))
            {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (joinId == null || joinId.length == 0) {
                    dialogBuilder.setTitle("ID Error!")
                    dialogBuilder.setMessage("Please enter your ID")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        joinFragmentBinding.joinId.requestFocus()
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
                        joinFragmentBinding.joinId.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }

            }
            /**
             *  유효성을 다 만족한다.
             */









            /** 비밀번호 유효성 설정하기
             */
            if(!Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",joinPw))
            {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (joinPw == null || joinPw.length == 0) {
                    dialogBuilder.setTitle("Password Error!")
                    dialogBuilder.setMessage("Please enter your Password")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        joinFragmentBinding.joinPw.requestFocus()
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
                        joinFragmentBinding.joinPw.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }
            }



            /**
             *  Id name 중복 체크
             */
            thread{

                val client = OkHttpClient()

                val site = "http://${ServerIP.serverIp}/idDuplicate"

                val builderData = FormBody.Builder()

                builderData.add("userId",joinId).build()

                val formData = builderData.build()

                val request = Request.Builder()
                    .url(site)
                    .post(formData)
                    .build()

                val response = client.newCall(request).execute()



                if(response.isSuccessful)
                {
                    val result_text = response.body?.string()!!.trim()

                    Log.i("Message", result_text)

                    /** id 가 중복일 경우
                     */
                    if (result_text.equals("This ID is being used by someone") )
                    {
                        activity?.runOnUiThread{
                            val dialog = AlertDialog.Builder(requireContext())
                            dialog.setTitle("ID Error!")
                            dialog.setMessage("$result_text")
                            dialog.setPositiveButton("Confirm",null)
                            dialog.show()
                        }
                    }
                    else if( ! result_text.equals("This ID is being used by someone") )
                    {
                        idDuplicateCheck = true;
                        Log.i("com","here")
                        val act = activity as MainActivity

                        act.userId= joinId
                        act.userPw=joinPw
                        act.fragmentController("nick", true , true )
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

        return joinFragmentBinding.root
    }
}