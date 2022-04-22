package com.sing.board4_3.MainFragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.R
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.FragmentRestoreBinding
import okhttp3.FormBody
import java.util.logging.Level.INFO
import kotlin.concurrent.thread


class RestoreFragment : Fragment() {

    /** view Binding */
    lateinit var binding :FragmentRestoreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // activity
        val act = activity as MainActivity

        // binding inintalize
        binding = FragmentRestoreBinding.inflate(layoutInflater)


        // binding toolbar name
        binding.toolbar.title = "Do you want to restore Your Account?"


        //  Dialog
        DialogEx().makeDialog(
            requireContext(),
            "Resigned Account!",
        "You have an account that has been resigned. Do you want to restore it?",
        "confirm")



        /** TEXT VIEW 의 색상 변경하기 -------------------- */


        // bring Text
        val getText = binding.restoreText.text.toString()

        Log.i("getText", "${getText}")

        val spannable = SpannableString(getText)

        // 변경할 문자열
        val changeText = "Account Recovery"

        // 변경할 문자열의 처음 위치
        val start = getText.indexOf(changeText)

        // 변경할 문자열의 끝 위
        val end = start + changeText.length


        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#669900")), start, end , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.restoreText.setText(spannable)


        /** TEXT VIEW 의 색상 변경하기 -------------------- */




        /** restoreButton 을 눌렀을 때 -------------------- */
        binding.restoreButton.setOnClickListener {


            // enter_string 에 입력한 값
            val enterString = binding.enterString.text.toString()





            /** 입력한 문자와 요구한 문자가 일치할 때 Thread 실행 */
            if( enterString.equals("Account Recovery") )
            {

                thread {
                    //thread 1 ------------

                    // activity
                    val email = act.email

                    val builder = FormBody.Builder()
                    builder.add("email", email)

                    val response = UseOkHttp().useThreadPatch("modify/restore", builder)

                    /** netWorking Success */
                    if(response.isSuccessful)
                    {
                        activity?.runOnUiThread {
                            DialogEx().makeDialogNoPositiveButton(
                                requireContext(),
                                "Restore Success",
                                "Restoration complete!! A new password has been sent to your email")
                        }

                        act.email = ""
                        val mainIntent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(mainIntent)
                        activity?.finish()



                    }


                    /** netWorking Fail */
                    else
                    {
                        activity?.runOnUiThread {
                            DialogEx().netWork(requireContext())
                        }
                    }


                } //thread 1 ------------

            }
            /** 입력한 문자와 요구한 문자가 일치하지 않을 */
            else
            {
                DialogEx().makeDialog(
                    requireContext(),
                    "The entered text does not match",
                    "Please check out",
                    "confirm")
            }

        }

        return binding.root
    }




}