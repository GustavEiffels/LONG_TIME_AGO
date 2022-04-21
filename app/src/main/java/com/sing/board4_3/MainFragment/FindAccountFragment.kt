package com.sing.board4_3.MainFragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.Support.ServerIP
import com.sing.board4_3.databinding.FragmentFindAccountBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread


class FindAccountFragment : Fragment() {

    lateinit var binding : FragmentFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFindAccountBinding.inflate(layoutInflater)

        binding.findAccountToolbar.title = "Find Your Account"


        val act = activity as MainActivity


        val initDial = AlertDialog.Builder(requireContext())
        initDial.setTitle("Notification")
        initDial.setMessage("If you have account in this app, we will sent you message about your account information")
        initDial.setPositiveButton("confirm",null)
        initDial.show()

        binding.findAccountEmail.requestFocus()


        binding.findButton.setOnClickListener {


            val email = binding.findAccountEmail.text.toString()

            if(!Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",email) )
            {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Email Input Error")
                dialog.setMessage("Email format is incorrect.")
                dialog.setPositiveButton("confirm",null)
                dialog.show()
                return@setOnClickListener
            }

            thread{
                val findAccount = OkHttpClient()

                val url = "http://${ServerIP.serverIp}/find/account"

                val builder = FormBody.Builder()


                val complete = builder.add("email",email).build()

                val request = Request.Builder().url(url).post(complete).build()

                val response = findAccount.newCall(request).execute()

                if(response.isSuccessful)
                {
                    val result = response.body?.string()!!.trim()

                    if( result.equals("exist") )
                    {
                        activity?.runOnUiThread {
                            DialogEx().makeDialogNoPositiveButton(
                                requireContext(),
                                "Email Sending complete",
                                "Please Check your Email")
                        }
                        act.fragmentController("login",false,false)
                        act.fragmentRemoveBackStack("find_account")

                    }
                    else
                    {
                        activity?.runOnUiThread {
                            DialogEx().makeDialog(
                                requireContext(),
                                "Registered information not found",
                                "Please check again",
                                "confirm"
                            )
                            binding.findAccountEmail.setText("")
                            binding.findAccountEmail.requestFocus()
                        }
                    }
                }
                else
                {
                    activity?.runOnUiThread {
                        DialogEx().netWork(requireContext())
                    }
                }
            }
        }









        return binding.root
    }

}