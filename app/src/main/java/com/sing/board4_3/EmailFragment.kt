package com.sing.board4_3

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.common.internal.FallbackServiceBroker
import com.sing.board4_3.databinding.FragmentEmailBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.threadName
import java.util.*
import java.util.regex.Pattern
import kotlin.concurrent.thread


class EmailFragment : Fragment() {


    lateinit var emailFragmentBinding : FragmentEmailBinding

    var isTimerRunning  = false

    lateinit var newtext: TextView

    // 총 소요 시간
    val totalTime:Long = 20000;

    // 남은 시간 --> 계속 변경될 거라 var 사용
    var leftTime = totalTime

    var timeView=""


    lateinit var  countdown : CountDownTimer




    fun updateText()
    {
        var minute = (leftTime/1000)/60
        var second = (leftTime/1000)%60


        newtext.text= String.format(Locale.getDefault(),"%02d:%02d",minute,second)
    }

    fun stopTimer()
    {

    }

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

        // binding 객체 재정의
        val binding = emailFragmentBinding


        newtext = binding.leftTime




        var emailAuthResult = true

        emailFragmentBinding.emailAuth.setOnClickListener {

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

                        /** 아이디가 중복 없고 사용 가능할 때 해당 이메일로 인증번호 보내기 -------------------------
                         */
                        else
                        {
                            activity?.runOnUiThread{

                                val dialog  = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                                dialog.setTitle("Message Send Complete")
                                dialog.setMessage("The verification code has been sent to that email.")
                                dialog.setPositiveButton("confirm",null)
                                dialog.show()



                                binding.emailAuth.visibility = View.GONE


                                binding.authSubmit.visibility = View.VISIBLE
                                binding.getSerial.visibility = View.VISIBLE
                                binding.authResend.visibility = View.VISIBLE
                                binding.leftTime.visibility = View.VISIBLE


                                var resultCode = ""

                                var authCode = ""

                                Log.i("Get email 1 ","${authCode}")

                                isTimerRunning = true

                                if(isTimerRunning)
                                {
                                   countdown = object  : CountDownTimer(leftTime, 1000){

                                        override fun onTick(p0: Long)
                                        {
                                            leftTime=p0
                                            updateText()
                                        }

                                        override fun onFinish() {
                                            isTimerRunning=false

                                            binding.leftTime.text="Time Out"
                                        }


                                    }.start()


                                    thread{
                                        val clientForAuthCheck = OkHttpClient()

                                        val site = "http://${ServerIP.serverIp}/email/auth"

                                        val authBuilder = FormBody.Builder()
                                        authBuilder.add("email",email)

                                        val authForEmail = authBuilder.build()

                                        val authEmail = Request.Builder().url(site).post(authForEmail).build()

                                        val authResponse = clientForAuthCheck.newCall(authEmail).execute()

                                        if(authResponse.isSuccessful)
                                        {
                                            authCode = authResponse.body?.string()!!.trim()



                                            Log.i("Get email 2","${authCode}")

                                            binding.authSubmit.setOnClickListener {

                                                val resultCode = binding.getSerial.text.toString()

                                                if( authCode.equals(resultCode) && isTimerRunning )
                                                {
                                                    isTimerRunning = false
                                                    activity?.runOnUiThread {

                                                        val authComplete = AlertDialog.Builder(requireContext())
                                                        authComplete.setTitle("authentication is complete")
                                                        authComplete.setPositiveButton("confirm",null)
                                                        authComplete.show()

                                                        binding.authSubmit.visibility = View.GONE
                                                        binding.getSerial.visibility = View.GONE
                                                        binding.authResend.visibility = View.GONE
                                                        binding.leftTime.visibility = View.GONE
                                                        binding.emailNextButton.visibility = View.VISIBLE


                                                        binding.emailNextButton.setOnClickListener {
                                                            val act = activity as MainActivity
                                                            act.email = email
                                                            act.fragmentController("join",true,true)
                                                        }

                                                    }
                                                }
                                                else
                                                {
                                                    val authComplete = AlertDialog.Builder(requireContext())
                                                    authComplete.setTitle("authentication Error")
                                                    authComplete.setMessage("Auth Value is different")
                                                    authComplete.setPositiveButton("confirm",null)
                                                    authComplete.show()
                                                }




                                            }
                                        }



                                    }
                                }

                                var tryTime = 3
                                /** 인증 번호 다시 보낼 때 ------------------------------------------------
                                 */
                                binding.authResend.setOnClickListener {

                                    tryTime--

                                    countdown.cancel()



                                    if(tryTime>0)
                                    {
                                        leftTime = totalTime

                                        isTimerRunning  = true

                                        if(isTimerRunning)
                                        {
                                            countdown =object : CountDownTimer(leftTime, 1000){

                                                override fun onTick(p0: Long)
                                                {
                                                    leftTime=p0
                                                    updateText()
                                                }

                                                override fun onFinish() {
                                                    isTimerRunning=false
                                                    val expire = AlertDialog.Builder(requireContext())
                                                    expire.setTitle("Time Out")
                                                    expire.setMessage("Please try again")
                                                    expire.setPositiveButton("confirm", null)
                                                    expire.show()

                                                    binding.leftTime.text="Time Out"
                                                }

                                            }.start()


                                            thread{
                                                val clientForAuthCheck = OkHttpClient()

                                                val site = "http://${ServerIP.serverIp}/email/auth"

                                                val authBuilder = FormBody.Builder()
                                                authBuilder.add("email",email)

                                                val authForEmail = authBuilder.build()

                                                val authEmail = Request.Builder().url(site).post(authForEmail).build()

                                                val authResponse = clientForAuthCheck.newCall(authEmail).execute()

                                                if(authResponse.isSuccessful)
                                                {

                                                    activity?.runOnUiThread{
                                                        val resendDial = AlertDialog.Builder(requireContext())
                                                        resendDial.setTitle("Message Resend Complete")
                                                        resendDial.setMessage("The verification code has been sent to that email.")
                                                        resendDial.setPositiveButton("confirm",null)
                                                        resendDial.show()
                                                    }

                                                    authCode = authResponse.body?.string()!!.trim()

                                                    Log.i("Get email 2","${authCode}")

                                                    binding.authSubmit.setOnClickListener {

                                                        val resultCode = binding.getSerial.text.toString()

                                                        if( authCode.equals(resultCode) && isTimerRunning )
                                                        {
                                                            isTimerRunning = false
                                                            activity?.runOnUiThread {

                                                                val authComplete = AlertDialog.Builder(requireContext())
                                                                authComplete.setTitle("authentication is complete")
                                                                authComplete.setPositiveButton("confirm",null)
                                                                authComplete.show()

                                                                binding.authSubmit.visibility = View.GONE
                                                                binding.getSerial.visibility = View.GONE
                                                                binding.authResend.visibility = View.GONE
                                                                binding.leftTime.visibility = View.GONE
                                                                binding.emailNextButton.visibility = View.VISIBLE






                                                                binding.emailNextButton.setOnClickListener {
                                                                    val act = activity as MainActivity

                                                                    act.email = email
                                                                    act.fragmentController("join",true,true)
                                                                }

                                                            }
                                                        }
                                                        else
                                                        {
                                                            val authComplete = AlertDialog.Builder(requireContext())
                                                            authComplete.setTitle("authentication Error")
                                                            authComplete.setMessage("Auth Value is different")
                                                            authComplete.setPositiveButton("confirm",null)
                                                            authComplete.show()
                                                        }



                                                    }
                                                }



                                            }
                                    }
                                    }
                                    else
                                    {
                                        val messageCountOver = AlertDialog.Builder(requireContext())
                                        messageCountOver.setTitle("Resend Error")
                                        messageCountOver.setMessage("The number of messages sent has been exceeded")
                                        messageCountOver.setPositiveButton("confirm",null)
                                        messageCountOver.show()

                                        val act = activity as MainActivity
                                        act.fragmentRemoveBackStack("email")
                                    }

                                }




                            }
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