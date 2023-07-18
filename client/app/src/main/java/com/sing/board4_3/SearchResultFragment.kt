package com.sing.board4_3

import android.app.AlertDialog
import android.app.appsearch.SearchResult
import android.content.Context
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
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sing.board4_3.databinding.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import kotlin.concurrent.thread


class SearchResultFragment : Fragment() {

    lateinit var searchResultFragmentBinding : FragmentSearchResultBinding

    // LIST 생성 --- 목록에 삽입하기 위해서 사용
    val contentIdxList = ArrayList<Int>()

    val contentWriterList = ArrayList<String>()

    val contentWriteDateList = ArrayList<String>()

    val contentSubjectList = ArrayList<String>()


    val contentImageUrl = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        searchResultFragmentBinding = FragmentSearchResultBinding.inflate(layoutInflater)


        // Toolbar 설정

        val act = activity as BoardMainActivity

        val query = act.query

        searchResultFragmentBinding.searchResultToolbar.title="Search Result of \"${query}\" "

        /** Toolbar 상단에 backButton 추가로 생성 -------------------------------------------------------
         */
        val navIcon = requireActivity().getDrawable(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

        searchResultFragmentBinding.searchResultToolbar.navigationIcon = navIcon


        // 색상을 변경 --- android 10 버전일 경우와 그렇지 않을경우 둘다 고려

        /** 현재 SDK Version 이 10 Version 이상인 경우 --------------------------------------------------
         */
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            searchResultFragmentBinding.
            searchResultToolbar.
            navigationIcon?.
            colorFilter= BlendModeColorFilter(Color.parseColor("#FFFFFF"), BlendMode.SRC_ATOP)
        }


        /** 현재 SDK Version 이 10 Version 미만인 경우 --------------------------------------------------
         */
        else
        {
            searchResultFragmentBinding.searchResultToolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP
            )
        }

        /** 화살표를 눌렀을 경우 현재 Fragment 를 backStack 에서 제거 ----------------------------------------
         */
        searchResultFragmentBinding.searchResultToolbar.setNavigationOnClickListener {
            val act = activity as BoardMainActivity
            act.fragmentRemoveBackStack("search_result")
        }




        /** Search view
         */
        searchResultFragmentBinding.searchResultSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                if(query.toString().length>1)
                {
                    act.query = query.toString()
                    act.fragmentController("search_result", true, true)
                }
                else
                {
                    Toast.makeText(requireContext(),"Please enter at least 2 characters", Toast.LENGTH_SHORT).show()

                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                return false
            }
        })



        /** RecyclerView 설정 ----
         */
        val searchResultRecyclerAdapter = SearchResultRecyclerAdapter()
        searchResultFragmentBinding.searchResultRecycler.adapter = searchResultRecyclerAdapter

        searchResultFragmentBinding.searchResultRecycler.layoutManager = LinearLayoutManager(requireContext())

        // 가로 세로 구분자 설정
        // 1 --> 가로방향으로
        // 0 ---> 세로 방향으로
        searchResultFragmentBinding.searchResultRecycler.addItemDecoration(DividerItemDecoration(requireContext(), 1))


        // Unlimited Scroll --> paging
        // addOnScrollListener() --> 스크롤 발생시 동작
        searchResultFragmentBinding.searchResultRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            // Scroll 이 완료되면 수행되는 method 를 선언
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // scroll 이 끝나는 항목은 현재 화면에 보이는 항목중 제일 미자막 항목의 index 임으로
                // 제일 마지막 항목의 index 를 가져온다
                val index1 = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                Log.i("마지막 index : ", "$index1")

                // index view 는 0 부터 시작


                // recycler view 가 관리하는 항목의 총 개수
                val count1 = recyclerView.adapter?.itemCount

                Log.i("관리하는 항목의 개 : ", "$count1")

                // recycler view 가 관리하는 수와 마지막 항목의 index +1 한 값이 같으면
                // page 를 다 봤다고 할 수 있다.
                if( index1 + 1 == count1 )
                {
                    // 같을 경우 page 를 +1 증가 시켜준다.
                    act.nowPage = act.nowPage + 1


                    getContentList(false)
                }
                Log.i("현재 page : ", "${act.nowPage}")

            }
        })



        // 기져오게 설정하기
        getContentList(true)



        return searchResultFragmentBinding.root
    }

    /**
     *  RecyclerViewAdapter -----------------------------------------
     */
    inner class SearchResultRecyclerAdapter: RecyclerView.Adapter<SearchResultRecyclerAdapter.ViewHolderClass>()
    {


        /** ViewHolder Create ------
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass
        {
            val searchResultRecyclerItemBinding = SearchResultRecylerviewBinding.inflate(layoutInflater)

            val holder = ViewHolderClass(searchResultRecyclerItemBinding)

            // 가로 , 세로
            val layoutParams = RecyclerView.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            searchResultRecyclerItemBinding.root.layoutParams = layoutParams
            searchResultRecyclerItemBinding.root.setOnClickListener(holder)

            return holder
        }



        override fun onBindViewHolder(holder: ViewHolderClass, position: Int)
        {
            // data 를 holder 에 넣기
            holder.searchResultItemNickname.text = contentWriterList[position]

            holder.searchResultItemWriteDate.text= contentWriteDateList[position]

            holder.searchResultItemSubject.text =contentSubjectList[position]
        }


        override fun getItemCount(): Int
        {
            return contentIdxList.size
        }
        /**
         *  ViewHolder Class--------------
         */
        inner class ViewHolderClass(searchResultRecyclerItemBinding: SearchResultRecylerviewBinding)
            :RecyclerView.ViewHolder(searchResultRecyclerItemBinding.root), View.OnClickListener
        {

            val searchResultItemNickname = searchResultRecyclerItemBinding.searchResultNickname
            val searchResultItemSubject = searchResultRecyclerItemBinding.searchResultSubject
            val searchResultItemWriteDate = searchResultRecyclerItemBinding.searchResultWriteDate

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

            // 다른 게시판을 고를 경우

            // 현재 게시판 리스트를 초기화 해주고 나서

            // page 번호도 초기화 해주어야 함
            val act = activity as BoardMainActivity

            act.nowPage = 1
        }

        val act = activity as BoardMainActivity

        val query = act.query

        if(query.length>1) {

            thread {
                val client = OkHttpClient()

                val site = "http://${ServerIP.serverIp}/search"

                // activity 가져오기
                val act = activity as BoardMainActivity

                val builder1 = FormBody.Builder()

                builder1.add("query", "${act.query}")
                builder1.add("limit", "${act.nowPage}")


                val formbody = builder1.build()

                val request = Request.Builder().url(site).post(formbody).build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val resultText = response.body?.string()!!.trim()

                    val root = JSONArray(resultText)

                    Log.i("query", "${act.query}")

                    for (i in 0 until root.length()) {
                        val obj = root.getJSONObject(i)

                        contentIdxList.add(obj.getInt("content_idx"))
                        contentWriterList.add(obj.getString("content_nick_name"))
                        contentWriteDateList.add(obj.getString("content_write_date"))
                        contentSubjectList.add(obj.getString("content_subject"))
                        contentImageUrl.add(obj.getString("content_image_url"))

                        Log.i("content_subject", "${obj.getString("content_subject")}")
                    }


                    if (root.length() == 0) {
                        // 빠질 것이 없다면 마지막 PAGE 로 고정
                        act.nowPage = act.nowPage - 1
                    }


                    activity?.runOnUiThread {
                        // recyclerView 갱신
                        searchResultFragmentBinding.searchResultRecycler.adapter?.notifyDataSetChanged()
                    }
                }

                else
                {
                    activity?.runOnUiThread {
                        val dialog = AlertDialog.Builder(requireContext())
                        dialog.setTitle("Net Work Error")
                        dialog.setMessage("Emerge Net Work Connection Problem \n please try few minute later")
                        dialog.setPositiveButton("confirm",null)
                        dialog.show()
                    }
                }
            }
        }
        else
        {
            Toast.makeText(requireContext(),"Please enter at least 2 characters", Toast.LENGTH_SHORT).show()

        }
    }

}


