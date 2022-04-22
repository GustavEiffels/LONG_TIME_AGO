package com.sing.board4_3.BoardFragment

import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sing.board4_3.Activity.BoardMainActivity
import com.sing.board4_3.Activity.MainActivity
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.BoardSettingRecyclerviewBinding
import com.sing.board4_3.databinding.FragmentSettingBinding
import okhttp3.FormBody
import org.json.JSONArray
import kotlin.concurrent.thread

class SettingFragment : Fragment() {


    /** View Binding */
    lateinit var binding: FragmentSettingBinding

    /** 게시글 번호 list */
    val contentIdxList = ArrayList<Int>()

    /** 게시글 작성일 list */
    val contentWriteDateList = ArrayList<String>()

    /** 게시글 제목 list */
    val contentSubjectList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**  binding initialize */
        binding = FragmentSettingBinding.inflate(layoutInflater)

        // SharedReference 에서 User Data 가져오기
        val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

        val loginUserNick = pref.getString("login_user_nick","")

        val act = activity as BoardMainActivity

        /**  Toolbar title 을 userNick 으로 생성 */
        binding.baordSettingToolbar.title=loginUserNick


        // BackButton  --------------------------------
        val navIcon = requireActivity().getDrawable(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

        binding.baordSettingToolbar.navigationIcon = navIcon


        // 색상을 변경 --- android 10 버전일 경우와 그렇지 않을 경우 둘다 고려
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            binding.
            baordSettingToolbar.
            navigationIcon?.
            colorFilter= BlendModeColorFilter(Color.parseColor("#FFFFFF"), BlendMode.SRC_ATOP)
        }
        else
        {
            binding.baordSettingToolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP
            )
        }



        // backButton 누르면 Backstack 에서 Fragment 삭제
        binding.baordSettingToolbar.setNavigationOnClickListener {
            val act = activity as BoardMainActivity
            act.fragmentRemoveBackStack("board_setting")
        }


        // BackButton  --------------------------------



        /** Logout Button 눌렀을 때 */
        binding.boardSettingLogout.setOnClickListener {


            // 자동 로그인 정보
            val autoLogin = pref.getInt("login_auto_login",-1)

            // 사용자 번호
            val userIdx = pref.getInt("login_user_idx",-1)

            /** Logout 실행 */
            thread{
                // thread 1 ----------

                val builder1 = FormBody.Builder()
                builder1.add("user_idx",userIdx.toString())
                builder1.add("user_auto_login",autoLogin.toString())

                /** LoginController ----------> logout */
                val response = UseOkHttp().useThread("login/logout",builder1)

                if(response.isSuccessful)
                { // response.isSuccessful 1 ----------

                    val editor = pref?.edit()
                    editor?.clear()
                    editor?.putInt("login_auto_login", 0)
                    editor?.commit()

                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(),"Logout complete", Toast.LENGTH_SHORT).show()

                        val mainActivity = Intent(requireContext(), MainActivity::class.java)
                        startActivity(mainActivity)
                        activity?.finish()
                    }

                }
                /** netWork Connecting fail*/
                else { activity?.runOnUiThread{DialogEx().netWork(requireContext())}

                } // response.isSuccessful 1 ----------


            }// thread 1 ----------

        } // boardSettingLogout.setOnClickListener  ------



        /** 비밀번호 변경 버튼 */
        binding.boardSettingPasswordChange.setOnClickListener {
            act.fragmentController("password_confirm", true, true)

        }


        /** 회원 탈퇴 버튼  */
        binding.boardSettingResign.setOnClickListener {
            act.fragmentController("password_resign", true, true)
        }



        /** RecyclerView 설정 */
        val boardSettingRecyclerAdapter = BoardSettingRecyclerAdapter()
        binding.boardSettingRecycler.adapter = boardSettingRecyclerAdapter

        binding.boardSettingRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.boardSettingRecycler.addItemDecoration(DividerItemDecoration(requireContext(), 1))


        // 기져오게 설정하기
        getContentList(true)



        return binding.root
    }


    /** RecyclerViewAdapter  */
    inner class BoardSettingRecyclerAdapter: RecyclerView.Adapter<BoardSettingRecyclerAdapter.ViewHolderClass>()
    {


        /** ViewHolder Class-------------- */
        inner class ViewHolderClass(boardSettingRecyclerItemBinding: BoardSettingRecyclerviewBinding)
            : RecyclerView.ViewHolder(boardSettingRecyclerItemBinding.root), View.OnClickListener
        {

            val boardSettingItemNickname = boardSettingRecyclerItemBinding.boardSettingItemNickname
            val boardSettingItemSubject = boardSettingRecyclerItemBinding.boardSettingItemSubject
            val boardSettingItemWriteDate = boardSettingRecyclerItemBinding.boardSettingItemWriteDate

            /** recycler view item 누를 때 */
            override fun onClick(p0: View?)
            {

                val act = activity as BoardMainActivity

                // 해당 글 번호 setting
                act.readContentidx =contentIdxList[adapterPosition]

                // 게시글을 읽을 수 있도록 설정
                act.fragmentController("board_read", true , true )
            }
        }


        /** ViewHolder Create  */
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

    /** 스크롤을 위한 함수 생성 */
    fun getContentList(clear:Boolean)
    {
        if(clear)
        {
            contentIdxList.clear()
            contentSubjectList.clear()
            contentWriteDateList.clear()
        }

        thread{
            // thread 1 -----------

            val builder1 = FormBody.Builder()


            val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

            // SharedReference 에서 회원번호 가져오기
            val loginUserIdx = pref.getInt("login_user_idx", -1)

            builder1.add("user_idx", loginUserIdx.toString())


            /** contentsController ----------> getPersonalContent  */
            val response = UseOkHttp().useThread("content/personal", builder1)


            /** Network connecting success */
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
                }

                activity?.runOnUiThread {
                    // recyclerView 갱신
                    binding.boardSettingRecycler.adapter?.notifyDataSetChanged()
                }
            }
            else { activity?.runOnUiThread { DialogEx().netWork(requireContext()) } }
        }
    }

}