package com.sing.board4_3

import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sing.board4_3.databinding.BoardMainRecyclerviewBinding
import com.sing.board4_3.databinding.BoardSettingRecyclerviewBinding
import com.sing.board4_3.databinding.FragmentSettingBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import kotlin.concurrent.thread

class BoardSettingFragment : Fragment() {


    //ViewBinding
    lateinit var boardSettingFragmentBinding: FragmentSettingBinding

    // LIST 생성 --- 목록에 삽입하기 위해서 사용
    val contentIdxList = ArrayList<Int>()

    val contentWriterList = ArrayList<String>()

    val contentWriteDateList = ArrayList<String>()

    val contentSubjectList = ArrayList<String>()


    val contentImageUrl = ArrayList<String>()






    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        boardSettingFragmentBinding = FragmentSettingBinding.inflate(layoutInflater)

        val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

        val loginUserNick = pref.getString("login_user_nick","")



        boardSettingFragmentBinding.baordSettingToolbar.title=loginUserNick


        // drawable 객체 생성
        val navIcon = requireActivity().getDrawable(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

        boardSettingFragmentBinding.baordSettingToolbar.navigationIcon = navIcon

        // 색상을 변경 --- android 10 버전일 경우와 그렇지 않을경우 둘다 고려


        // Q
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            boardSettingFragmentBinding.
            baordSettingToolbar.
            navigationIcon?.
            colorFilter= BlendModeColorFilter(Color.parseColor("#FFFFFF"), BlendMode.SRC_ATOP)
        }
        else
        {
            boardSettingFragmentBinding.baordSettingToolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP
            )
        }

        boardSettingFragmentBinding.baordSettingToolbar.setNavigationOnClickListener {
            val act = activity as BoardMainActivity
            act.fragmentRemoveBackStack("board_setting")

        }


        /**
         *  Logout 버튼 구현 -------------------------------------__---------------------____--------
         *
         *  로그아웃 시 자동 로그인 해제
         *  MainActivity 시작
         *  BoardActivity 종료
         *  현재 Fragment 종료
         */
        boardSettingFragmentBinding.boardSettingLogout.setOnClickListener {

            val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

            // AutoLogin 정보를 0 으로 변경
            val editor = pref?.edit()
            editor?.putInt("login_auto_login", 0)
            editor?.commit()



            val autoLogin = pref.getInt("login_auto_login",-1)

            Log.i("login_user_auto", "$autoLogin")

            val userIdx = pref.getInt("login_user_idx",-1)

            // Database 에 자동 로그인 정보 변경하기

            thread{
                val client = OkHttpClient()
                val site = "http://${ServerIP.serverIp}/login/logout"

                val builder1 = FormBody.Builder()
                builder1.add("user_idx",userIdx.toString())
                builder1.add("user_auto_login",autoLogin.toString())

                val form = builder1.build()

                val request = Request.Builder().url(site).post(form).build()

                val response = client.newCall(request).execute()

                if(response.isSuccessful)
                {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(),"Logout complete", Toast.LENGTH_SHORT).show()

                        val mainActivity = Intent(requireContext(), MainActivity::class.java)
                        startActivity(mainActivity)
                        activity?.finish()
                    }
                }
            }

        }
        /**
         *  ========= 비밀번호 변경 기능 구현
         *
         *  비밀번호 두개 입력 받는다
         *  다른 Fragment 에서 수행 하도록 할 예정
         *  Fragment 2개 생성
         *  비밀번호 변경시 현재 비밀번호를 입력받게 만든다
         *  유효성 검사 적용
         *  비밀번호가 변경되면 다시 로그인 하게 만들자
         */
        boardSettingFragmentBinding.boardSettingPasswordChange.setOnClickListener {

            val act = activity as BoardMainActivity

            act.fragmentController("password_confirm", true, true)

        }



        /** RecyclerView 설정 ----
         */
        val boardSettingRecyclerAdapter = BoardSettingRecyclerAdapter()
        boardSettingFragmentBinding.boardSettingRecycler.adapter = boardSettingRecyclerAdapter

        boardSettingFragmentBinding.boardSettingRecycler.layoutManager = LinearLayoutManager(requireContext())

        // 가로 세로 구분자 설정
        // 1 --> 가로방향으로
        // 0 ---> 세로 방향으로
        boardSettingFragmentBinding.boardSettingRecycler.addItemDecoration(DividerItemDecoration(requireContext(), 1))


        // 기져오게 설정하기
        getContentList(true)



        return boardSettingFragmentBinding.root
    }


    /**
     *  RecyclerViewAdapter -----------------------------------------
     */
    inner class BoardSettingRecyclerAdapter: RecyclerView.Adapter<BoardSettingRecyclerAdapter.ViewHolderClass>()
    {


        /**
         *  ViewHolder Class--------------
         */
        inner class ViewHolderClass(boardSettingRecyclerItemBinding: BoardSettingRecyclerviewBinding)
            : RecyclerView.ViewHolder(boardSettingRecyclerItemBinding.root), View.OnClickListener
        {

            val boardSettingItemNickname = boardSettingRecyclerItemBinding.boardSettingItemNickname
            val boardSettingItemSubject = boardSettingRecyclerItemBinding.boardSettingItemSubject
            val boardSettingItemWriteDate = boardSettingRecyclerItemBinding.boardSettingItemWriteDate


            override fun onClick(p0: View?)
            {
                // BoardMainActivity Instance
                val act = activity as BoardMainActivity

                // 해당 글 번호 setting
                act.readContentidx =contentIdxList[adapterPosition]

                // Fragment 가 실행되도록 설정
                act.fragmentController("board_read", true , true )
            }
        }


        /** ViewHolder Create ------
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass
        {
            val boardSettingRecyclerItemBinding = BoardSettingRecyclerviewBinding.inflate(layoutInflater)

            val holder = ViewHolderClass(boardSettingRecyclerItemBinding)

            // 가로 , 세로
            val layoutParams = RecyclerView.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            boardSettingRecyclerItemBinding.root.layoutParams = layoutParams
            boardSettingRecyclerItemBinding.root.setOnClickListener(holder)

            return holder
        }



        override fun onBindViewHolder(holder: ViewHolderClass, position: Int)
        {

            val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

            val loginUserNick = pref.getString("login_user_nick","")

            holder.boardSettingItemNickname.text = loginUserNick

            holder.boardSettingItemWriteDate.text= contentWriteDateList[position]

            holder.boardSettingItemSubject.text =contentSubjectList[position]
        }


        override fun getItemCount(): Int
        {
            return contentIdxList.size
        }
    }

    /** 함수 생성
     */
    fun getContentList(clear:Boolean)
    {
        if(clear)
        {
            contentIdxList.clear()
            contentWriterList.clear()
            contentSubjectList.clear()
            contentWriteDateList.clear()
        }

        thread{
            val client = OkHttpClient()

            val site = "http://${ServerIP.serverIp}/content/getPrivateContentInfo"


            val builder1 = FormBody.Builder()

            val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

            // 초기값은 -1
            val loginUserIdx = pref.getInt("login_user_idx", -1)

            builder1.add("user_idx", loginUserIdx.toString())

            Log.i("user_idx","$loginUserIdx")
            val formbody = builder1.build()

            val request = Request.Builder().url(site).post(formbody).build()

            val response = client.newCall(request).execute()

            if(response.isSuccessful)
            {
                val resultText = response.body?.string()!!.trim()

                val root = JSONArray(resultText)

                for(i in 0 until root.length())
                {
                    val obj = root.getJSONObject(i)

                    contentIdxList.add(obj.getInt("content_idx"))
                    contentWriteDateList.add( obj.getString("content_write_date") )
                    contentSubjectList.add( obj.getString("content_subject") )
                    contentImageUrl.add( obj.getString("content_image_url") )
                }


                activity?.runOnUiThread {
                    // recyclerView 갱신
                    boardSettingFragmentBinding.boardSettingRecycler.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

}