package com.sing.board4_3.MainFragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.FragmentResetPasswordBinding
import okhttp3.FormBody
import java.util.regex.Pattern
import kotlin.concurrent.thread


/** FindController ---> findPassword */
class ResetPasswordFragment : Fragment() {

    // Binding
    lateinit var  binding : FragmentResetPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // binding initialize
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)

        // binding toolbar title setting
        binding.resetPasswordToolbar.title = "Reset Password"

        val act = activity as MainActivity


        // init dialog
        DialogEx().makeDialog(
            requireContext(),
            "Notification",
        "If an account exists  when you entered the email and ID  registered during registration," +
                " a new temporary password  will be sent to that email.",
            "confirm"
        )

        /** restButton */
        binding.restButton.setOnClickListener {

            // restButton.setOnClickListener -------------------

            val email = binding.resetPasswordEmail.text.toString()

            val id = binding.resetPasswordId.text.toString()

            /** email 유효성 검사 */
            if(!Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",email) )
            {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Email Input Error")
                dialog.setMessage("Email format is incorrect.")
                dialog.setPositiveButton("confirm",null)
                dialog.show()
                return@setOnClickListener
            }

            /** id 유효성 검사 */
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
                // thread 1 -------------------------


                val builder = FormBody.Builder()
                builder.add("email",email)
                builder.add("id",id)

                /** FindController ---> findPassword */
                val response = UseOkHttp().useThread("find/password",builder)

                /** netWorking Success */
                if(response.isSuccessful)
                { // netWorking Success ---------------------

                    val result = response.body?.string()!!.trim()

                    /** 계정이 존재하지 않을 때 */
                    if(result.equals("NotExist"))
                    {
                        activity?.runOnUiThread {
                            // runOnUiThread -------------------

                            binding.resetPasswordId.setText("")
                            binding.resetPasswordEmail.setText("")

                            DialogEx().makeDialog(
                                requireContext(),
                                "Failed to find member information",
                                "No accounts were found that match the information you entered. Please check again",
                                "confirm"
                            )

                        } // runOnUiThread -------------------

                    }  // 계정이 존재하지 않을 때 ---------------------



                    /** 탈퇴한 계정일 경우 */
                    // 계정 복구 Fragment 로 이동
                    else if(result.equals("NotAvailable"))
                    {

                        act.email = email
                        act.fragmentController("restore",true,true)
                    }

                    else
                    {
                        activity?.runOnUiThread {
                            // runOnUiThread -----------------------------

                            DialogEx().makeDialogNoPositiveButton(
                                requireContext(),
                                "Password change complete",
                            "The changed password has been sent to the registered email")
                        }

                        val mainIntent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(mainIntent)
                        activity?.finish()

                    }// runOnUiThread -----------------------------


                }// netWorking Success ---------------------

                else
                {
                    activity?.runOnUiThread {
                        DialogEx().netWork(requireContext())
                    }

                }

            }// thread 1 -------------------------

        } // restButton.setOnClickListener -------------------


        return binding.root
    }


}