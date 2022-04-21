package com.sing.board4_3.BoardFragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.Support.ServerIP
import com.sing.board4_3.databinding.FragmentResignBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread


class ResignFragment : Fragment() {

    lateinit var binding: FragmentResignBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResignBinding.inflate(layoutInflater)

        binding.resignToolbar.title="Resign"

        val initDial = AlertDialog.Builder(requireContext())
        initDial.setTitle("Resign")
        initDial.setMessage("If you wanna resign please follow the sentence, Your information will be delete")
        initDial.setPositiveButton("confirm",null)
        initDial.show()


        binding.resignButton.setOnClickListener {

            val resultMessage = binding.followMessage.text.toString()

            if( resultMessage.equals("I will leave") )
            {
                val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

                // 초기값은 -1
                val loginUserIdx = pref.getInt("login_user_idx", -1)

                thread{

                    val resign = OkHttpClient()
                    val url = "http://${ServerIP.serverIp}/login/resign"

                    val builder = FormBody.Builder()
                    val complete = builder.add("idx",loginUserIdx.toString()).build()

                    val request = Request.Builder().url(url).patch(complete).build()

                    val response = resign.newCall(request).execute()

                    if(response.isSuccessful)
                    {
                        val editor = pref?.edit()
                        editor?.clear()
                        editor?.putInt("login_auto_login", 0)
                        editor?.commit()

                        activity?.runOnUiThread {
                            Toast.makeText(requireContext(),"Resign Complete See you Again", Toast.LENGTH_SHORT).show()

                            val mainActivity = Intent(requireContext(), MainActivity::class.java)
                            startActivity(mainActivity)
                            activity?.finish()
                        }
                    }
                    else
                    {
                        DialogEx().netWork(requireContext())
                    }

                }
            }
            else
            {
                DialogEx().makeDialog(requireContext(),
                    "Wrong Message",
                    "The sentences do not match.",
                    "confirm")
            }
        }


        return binding.root
    }


}