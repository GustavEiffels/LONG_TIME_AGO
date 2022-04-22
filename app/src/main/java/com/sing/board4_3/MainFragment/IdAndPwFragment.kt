package com.sing.board4_3.MainFragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.FragmentJoinBinding
import okhttp3.FormBody
import java.util.regex.Pattern
import kotlin.concurrent.thread


class IdAndPwFragment : Fragment() {


    lateinit var binding : FragmentJoinBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentJoinBinding.inflate(inflater)

        binding.joinToolbar.title="Id And Password Setting"


        /** joniNextBtn 을 눌렀을 때 처리 --------------------------------------------------------------
         */
        binding.joinNextBtn.setOnClickListener {


            /**
             *  text 를 사용해서 가져오면 editable 객체로 가져오기 때문에
             *  toString 해주어야 한다,
             */

            val joinId = binding.joinId.text.toString()
            val joinPw = binding.joinPw.text.toString()


            /** Id 유효성 검사 -----
             */
            if(!Pattern.matches("^[a-zA-Z]{2}[a-zA-Z0-9_]{4,11}$",joinId))
            {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                if (joinId == null || joinId.length == 0) {
                    dialogBuilder.setTitle("ID Error!")
                    dialogBuilder.setMessage("Please enter your ID")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        binding.joinId.requestFocus()
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
                        binding.joinId.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }

            }


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
                        binding.joinPw.requestFocus()
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
                        binding.joinPw.requestFocus()
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
            //------------------------------------------------- thread1
                val builderData = FormBody.Builder()
                builderData.add("userId",joinId).build()

                /** JoinController -------> idDuplicate : idDuplicateCheck */
                val response = UseOkHttp().useThread("join/idDuplicate",builderData)

                /** Email Connection Fail*/
                if(response.isSuccessful)
                {
                    val result_text = response.body?.string()!!.trim()


                    // Id 가 중복일 경우
                    if (result_text.equals("N") )
                    {
                        activity?.runOnUiThread{

                            DialogEx().makeDialog(
                                requireContext(),
                                "ID Error!",
                                "This ID is being used by someone",
                                "Confirm" )
                        }
                    }

                    // Id 가 중복이 아닐 경우
                    else
                    {
                        val act = activity as MainActivity

                        act.userId= joinId
                        act.userPw=joinPw
                        act.fragmentController("nick", true , true )
                    }
                }

                /** Net Work Connection fail */
                else
                {
                    activity?.runOnUiThread{
                        DialogEx().netWork(requireContext())
                    }
                }
            }//------------------------------------------------- thread1


        }

        return binding.root
    }
}