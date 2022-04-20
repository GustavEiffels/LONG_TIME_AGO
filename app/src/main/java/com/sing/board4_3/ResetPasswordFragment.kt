package com.sing.board4_3

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sing.board4_3.databinding.FragmentResetPasswordBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import kotlin.concurrent.thread


class ResetPasswordFragment : Fragment() {

    lateinit var  binding : FragmentResetPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResetPasswordBinding.inflate(layoutInflater)


        binding.resetPasswordToolbar.title = "Reset Password"

        val act = activity as MainActivity


        DialogEx().makeDialog(
            requireContext(),
            "Notification",
        "If an account exists  when you entered the email and ID  registered during registration," +
                " a new temporary password  will be sent to that email.",
            "confirm"
        )

        binding.restButton.setOnClickListener {

            val email = binding.resetPasswordEmail.text.toString()

            val id = binding.resetPasswordId.text.toString()


            if(!Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",email) )
            {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Email Input Error")
                dialog.setMessage("Email format is incorrect.")
                dialog.setPositiveButton("confirm",null)
                dialog.show()
                return@setOnClickListener
            }

            if(!Pattern.matches("^[a-zA-Z]{2}[a-zA-Z0-9_]{4,11}$",id))
            {

                val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                if (id == null || id.length == 0) {
                    dialogBuilder.setTitle("ID Error!")
                    dialogBuilder.setMessage("Please enter your ID")
                    dialogBuilder.setPositiveButton("Confirm")
                    { dialogInterface: DialogInterface, i: Int ->
                        binding.resetPasswordId.requestFocus()
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
                        binding.resetPasswordId.requestFocus()
                    }
                    dialogBuilder.show()

                    // lambda
                    return@setOnClickListener
                }

            }


            thread{
                val resetPassword = OkHttpClient()

                val url = "http://${ServerIP.serverIp}/find/password"

                val builder = FormBody.Builder()

                builder.add("email",email)
                builder.add("id",id)

                val complete = builder.build()

                val request = Request.Builder().url(url).post(complete).build()

                val response = resetPassword.newCall(request).execute()

                if(response.isSuccessful)
                {
                    val result = response.body?.string()!!.trim()

                    if(result.equals("not"))
                    {
                        activity?.runOnUiThread {

                            binding.resetPasswordId.setText("")
                            binding.resetPasswordEmail.setText("")

                            DialogEx().makeDialog(
                                requireContext(),
                                "Failed to find member information",
                                "No accounts were found that match the information you entered. Please check again",
                                "confirm"
                            )

                        }
                    }
                    else
                    {
                        activity?.runOnUiThread {

                            DialogEx().makeDialogNoPositiveButton(
                                requireContext(),
                                "Password change complete",
                            "The changed password has been sent to the registered email")

                            act.fragmentController("login",false,false)
                            act.fragmentRemoveBackStack("reset_password")
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