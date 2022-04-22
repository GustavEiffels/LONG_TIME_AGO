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
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.FragmentEmailBinding
import okhttp3.FormBody
import java.util.*
import java.util.regex.Pattern
import kotlin.concurrent.thread


class EmailFragment : Fragment() {

    /** viewBinding
     */
    lateinit var binding : FragmentEmailBinding

    /** 타이머 작동여부 확인 Boolean
     */
    var isTimerRunning  = false

    /** 초기화 할 TEXT View
     */
    lateinit var newtext: TextView

    /** 인증을 위한 총 시간
     */
    val totalTime:Long = 20000;

    /** 인증번호 만료까지 남은 시간
     */
    var leftTime = totalTime

    var timeView=""

    /** CountDownTimer Class 를 받을 변수
     */
    lateinit var  countdown : CountDownTimer


    /** 시간이 지날 때 마다 , 시간을 표현하기 위한 method */
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

        // binding
        binding = FragmentEmailBinding.inflate(layoutInflater)

        binding.emailFragmentToolBar.title = "Enter your Email"


        newtext = binding.leftTime


        var emailAuthResult = true

        /** 이메일 인증 버튼 눌렀을 경우
         */
        binding.emailAuth.setOnClickListener {

            // 입력받은 Email 값을 저장할 변수
            val email = binding.getEmail.text.toString()


            /** 이메일 인증을 받지 않았을 경우 --------------------
             */
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


                    /** JoinController */
                    val response = UseOkHttp().useThread("join/emailCheck", formBody)

                    if(response.isSuccessful)
                    {


                        val result = response.body?.string()!!.trim()

                        if(result.equals("available"))
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
                         /** 탈퇴한 경우 */
                         else if(result.equals("NotAvailable"))
                        {
                            val act = activity as MainActivity
                            act.email = email
                            act.fragmentController("restore",true, true )
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



                                // Resend Button 활성화 여부
                                var isResend = false


                                // Timer 작동 여부
                                isTimerRunning = true

                                // Timer 가 정지 여부
                                var timeStop = false


                                countDownThread(email, binding, isResend, timeStop)

                                // 인증번호 요청 3번 초과 시 LoginFragment 로 돌아가게 하기 위함 -----
                                var tryTime = 3



                                /** 인증 번호 다시 보낼 때 ------------------------------------------------
                                 */
                                binding.authResend.setOnClickListener {
                                    // authResend.setOnClickListener -----

                                    isResend = true

                                    tryTime--

                                    countdown.cancel()

                                    /**  3번 초과하지 않았을 때,
                                     */
                                    if(tryTime>0)
                                    {
                                        leftTime = totalTime

                                        isTimerRunning  = true

                                        countDownThread(email, binding, isResend, timeStop)

                                    }
                                    /**  3번 초과 했을 경우
                                     */
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

                                }// authResend.setOnClickListener -----


                            }
                        }


                    }
                    /** NetWorking Fail*/
                    else
                    {
                        activity?.runOnUiThread {
                            DialogEx().netWork(requireContext())
                        }
                    }
                }


            }
        }

        return binding.root
    }


    fun countDownThread(email:String,  binding:FragmentEmailBinding, isResend: Boolean, timeStop: Boolean )
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
                            "confirm" )
                    }

                    binding.leftTime.text = "Time Out"
                }

            }.start()

            thread {


                val authBuilder = FormBody.Builder()
                authBuilder.add("email", email)

                /** EmailController ----> auth : emailAuth */
                val authResponse = UseOkHttp().useThread("email/auth",authBuilder)

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

                        val userInputCode = binding.getSerial.text.toString()

                        if (authCode.equals(userInputCode) && isTimerRunning)
                        {
                            isTimerRunning = false

                            timeStop = true

                            activity?.runOnUiThread {

                                DialogEx().makeDialog(requireContext(),
                                    "authentication is complete",
                                "",
                                "confirm")

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
                        }
                        else
                        {

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