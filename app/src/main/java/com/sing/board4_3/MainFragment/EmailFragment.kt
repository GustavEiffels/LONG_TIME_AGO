package com.sing.board4_3.MainFragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.ServerIP
import com.sing.board4_3.Support.ThreadMethod
import com.sing.board4_3.databinding.FragmentEmailBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.regex.Pattern
import kotlin.concurrent.thread


class EmailFragment : Fragment() {

    lateinit var binding : FragmentEmailBinding

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



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentEmailBinding.inflate(layoutInflater)

        binding.emailFragmentToolBar.title = "Enter your Email"




        newtext = binding.leftTime


        var emailAuthResult = true

        binding.emailAuth.setOnClickListener {

            // 입력받은 Email 값을 저장할 변수
            val email = binding.getEmail.text.toString()


            if( !emailAuthResult )
            {
                DialogEx().makeDialog(
                    requireContext(),
                    "Auth Error",
                    "Please get certified",
                    "confirm")
                return@setOnClickListener
            }

            else
            {
                if(!Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",email) )
                {

                    DialogEx().makeDialog(
                        requireContext(),
                        "Email Input Error",
                        "Email format is incorrect.",
                    "confirm")

                    return@setOnClickListener
                }


                thread {


                    val formBody = FormBody.Builder()
                    formBody.add("email",email).build()

                    val response = ThreadMethod().useThread("login/emailCheck", formBody)

                    if(response.isSuccessful)
                    {

                            // true ---> 사용가능
                        val result = response.body?.string()!!.trim()

                        if(!result.equals("null"))
                        {
                            activity?.runOnUiThread{
                                val dialog = AlertDialog.Builder(requireContext())
                                dialog.setTitle("Email Duplicate")
                                dialog.setMessage("This Email already used by someone")
                                dialog.setPositiveButton("confirm")
                                { dialogInterface: DialogInterface, i: Int ->
                                    binding.getEmail.setText("")
                                    binding.getEmail.requestFocus()
                                }
                                dialog.show()
                            }
                        }

                        /** 아이디가 중복 없고 사용 가능할 때 해당 이메일로 인증번호 보내기 -------------------------
                         */
                        else
                        {
                            activity?.runOnUiThread{

                                DialogEx().makeDialog(
                                    requireContext(),
                                    "Message Send Complete",
                                    "The verification code has been sent to that email.",
                                    "confirm"
                                )


                                binding.emailAuth.visibility = View.GONE
                                binding.authSubmit.visibility = View.VISIBLE
                                binding.getSerial.visibility = View.VISIBLE
                                binding.authResend.visibility = View.VISIBLE
                                binding.leftTime.visibility = View.VISIBLE


                                var resultCode = ""

                                var authCode = ""

                                var isResend = false


                                isTimerRunning = true

                                var timeStop = false


                                countDownThread(email, authCode, binding, isResend, timeStop)


                                var tryTime = 3
                                /** 인증 번호 다시 보낼 때 ------------------------------------------------
                                 */
                                binding.authResend.setOnClickListener {

                                    isResend = true

                                    tryTime--

                                    countdown.cancel()

                                    if(tryTime>0)
                                    {
                                        leftTime = totalTime

                                        isTimerRunning  = true

                                        countDownThread(email, authCode, binding, isResend, timeStop)

                                    }
                                    else
                                    {
                                        DialogEx().makeDialog(
                                            requireContext(),
                                            "Resend Error",
                                            "The number of messages sent has been exceeded",
                                            "confirm" )

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
                        DialogEx().netWork(requireContext())
                    }
                }


            }
        }

        return binding.root
    }


    fun countDownThread(email:String, authCode:String, binding:FragmentEmailBinding, isResend: Boolean, timeStop: Boolean )
    {

        var timeStop = false
        if(isTimerRunning)
        {
            countdown = object : CountDownTimer(leftTime, 1000) {

                override fun onTick(p0: Long) {
                    leftTime = p0
                    updateText()
                }

                override fun onFinish() {

                    isTimerRunning = false

                    if (!timeStop)
                    {
                        DialogEx().makeDialog(
                            requireContext(),
                            "Time Out",
                            "Please try again",
                            "confirm"
                        )
                    }

                    binding.leftTime.text = "Time Out"
                }

            }.start()

            thread {
                val clientForAuthCheck = OkHttpClient()

                val site = "http://${ServerIP.serverIp}/email/auth"

                val authBuilder = FormBody.Builder()
                authBuilder.add("email", email)

                val authForEmail = authBuilder.build()

                val authEmail = Request.Builder().url(site).post(authForEmail).build()

                val authResponse = clientForAuthCheck.newCall(authEmail).execute()

                if (authResponse.isSuccessful) {

                    if(isResend)
                    {
                        activity?.runOnUiThread {

                            DialogEx().makeDialog(
                                requireContext(),
                                "Message Resend Complete",
                                "The verification code has been sent to that email.",
                                "confirm"
                            )
                        }
                    }

                    var authCode = authResponse.body?.string()!!.trim()


                    binding.authSubmit.setOnClickListener {

                        val resultCode = binding.getSerial.text.toString()

                        if (authCode.equals(resultCode) && isTimerRunning)
                        {
                            isTimerRunning = false

                            timeStop = true

                            activity?.runOnUiThread {

                                val authComplete = AlertDialog.Builder(requireContext())
                                authComplete.setTitle("authentication is complete")
                                authComplete.setPositiveButton("confirm", null)
                                authComplete.show()

                                binding.authSubmit.visibility = View.GONE
                                binding.getSerial.visibility = View.GONE
                                binding.authResend.visibility = View.GONE
                                binding.leftTime.visibility = View.GONE
                                binding.emailNextButton.visibility = View.VISIBLE



                                binding.emailNextButton.setOnClickListener {
                                    val act = activity as MainActivity

                                    act.email = email
                                    act.fragmentController("join", true, true)
                                }

                            }
                        } else {

                            DialogEx().makeDialog(
                                requireContext(),
                                "authentication Error",
                                "Auth Value is different",
                                "confirm"
                            )
                        }


                    }
                }


            }
        }
    }
}