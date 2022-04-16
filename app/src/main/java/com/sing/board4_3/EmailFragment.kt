package com.sing.board4_3

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sing.board4_3.databinding.FragmentEmailBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread


class EmailFragment : Fragment() {


    lateinit var emailFragmentBinding : FragmentEmailBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        emailFragmentBinding = FragmentEmailBinding.inflate(layoutInflater)

        emailFragmentBinding.emailFragmentToolBar.title = "Enter your Email"




        var emailAuthResult = true

        emailFragmentBinding.emailButton.setOnClickListener {

            // 입력받은 Email 값을 저장할 변수
            val email = emailFragmentBinding.getEmail.text.toString()

            if( !emailAuthResult )
            {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Auth Error")
                dialog.setMessage("Please get certified")
                dialog.setPositiveButton("confirm",null)
                dialog.show()
                return@setOnClickListener
            }
            else
            {
                if(!Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",email) )
                {
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("Email Input Error")
                    dialog.setMessage("Email format is incorrect.")
                    dialog.setPositiveButton("confirm",null)
                    dialog.show()
                    return@setOnClickListener
                }


                thread {

                    val client = OkHttpClient()

                    val site = "http://${ServerIP.serverIp}/login/emailCheck"

                    val builder1 = FormBody.Builder()
                    builder1.add("email",email)

                    val form = builder1.build()

                    val request = Request.Builder().url(site).post(form).build()

                    val response = client.newCall(request).execute()

                    if(response.isSuccessful)
                    {

                            // true ---> 사용가능
                        val result = response.body?.string()!!.trim()

                        Log.i("result boolean", "test")
                        Log.i("result boolean", "${result}")

                        if(!result.equals("null"))
                        {
                            activity?.runOnUiThread{
                                val dialog = AlertDialog.Builder(requireContext())
                                dialog.setTitle("Email Duplicate")
                                dialog.setMessage("This Email already used by someone")
                                dialog.setPositiveButton("confirm")
                                { dialogInterface: DialogInterface, i: Int ->
                                    emailFragmentBinding.getEmail.setText("")
                                    emailFragmentBinding.getEmail.requestFocus()
                                }
                                dialog.show()
                            }
                        }

                        // 사용가능
                        else
                        {
                            val act = activity as MainActivity
                            act.email = email
                            act.fragmentController("join",true, true)
                        }


                    }
                    // 통신 실패
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









        return emailFragmentBinding.root
    }


}