package com.sing.board4_3.BoardFragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sing.board4_3.Activity.BoardMainActivity
import com.sing.board4_3.R
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.ServerIP
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.BoardMainRecyclerviewBinding
import com.sing.board4_3.databinding.FragmentBoardMainBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import kotlin.concurrent.thread


class MainFragment : Fragment() {


    /** 정해진 Paging size 보다 적은양의 데이터들을 들고왔을 경우
     * 해당 page 가 마지막 page 이기 때문에 isPageMax 를 true 로 변경하여
     * page 가 이후 증가하는 것을 방지한다.*/
    var isPageMax = false

    /** Page 변경 가능 여부 */
    /**
     *  paging 을 시도할 item 영역에서 벗어나지 못하면 ,  touch 했을 때 ,
     *  paging 이 영역안에서의 터치 횟수만큼 호출되기 때문에 사실상
     *  Paging의 의미가 사라진다.
     *  Paging 이 일어나는 method 에 조건문을 추가해서
     *  isPageUp 이 true 일 때만 paging 이 이루어지게 설정
     *  만약 Paging 이 한번 일어나면 isPageUP 객체를 false 로 변환해서
     *  이후 Paging 발생 영역안에서 여러번의 터치가 이루어진다고해도 호출되지 않는다.
     *  Paging 발생 영역보다 초과할 경우 조건문을 만들어
     *  isPageUp member 를 true 로 변경한다.
     * */
    var isPageUP = true


    // Binding
    lateinit var boardMainFragmentBinding : FragmentBoardMainBinding

    // LIST 생성 --- 목록에 삽입하기 위해서 사용
    val contentIdxList = ArrayList<Int>()

    val contentWriterList = ArrayList<String>()

    val contentWriteDateList = ArrayList<String>()

    val contentSubjectList = ArrayList<String>()


    val contentImageUrl = ArrayList<String>()

    /** 글 내용을 담을 변수 선언 */
    val contentContext = ArrayList<String>()


    lateinit var googleNick:String


    lateinit var show:TextView



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        // BoardMainActivity 를 불러옴
        val act = activity as BoardMainActivity



        // Binding
        boardMainFragmentBinding = FragmentBoardMainBinding.inflate(inflater)


        boardMainFragmentBinding.boardToolbar.title= act.boardNameList[act.selectedBoardType]


        /** Toolbar 에 Menu 적용 */
        boardMainFragmentBinding.boardToolbar.inflateMenu(R.menu.board_main_menu)


        /** Tool bar 의 menu 를 들고올 경우 */
        boardMainFragmentBinding.boardToolbar.setOnMenuItemClickListener {

            when(it.itemId)
            {
                /** 다른 게시판 종류를 선택할 수 있는 spinner 를 눌렀을 경우 */
                R.id.board_main_menu_board_list ->
                {
                    // Board Main Activity 를 부름
                    val act = activity as BoardMainActivity

                    // alert 를 생성
                    val boardListBuilder = AlertDialog.Builder(requireContext())
                    boardListBuilder.setTitle("Board List")
                    boardListBuilder.setNegativeButton("Cancel",null)
                    boardListBuilder.setItems(act.boardNameList.toTypedArray())
                    {
                            dialogInterface: DialogInterface, i: Int ->
                            act.selectedBoardType = i

                        // 이전에 있던 목록들 비우기
                        getContentList(true)


                        // toolbar 이름 변경하기
                        boardMainFragmentBinding.boardToolbar.title = act.boardNameList[act.selectedBoardType]
                    }
                    boardListBuilder.show()
                    true
                }

                /** 메뉴에서 글쓰기 버튼을 눌렀을 경우 */
                R.id.board_main_menu_write ->
                {

                    // Activity 변경
                    val act = activity as BoardMainActivity
                    act.fragmentController("board_write",true, true)
                    true
                }

                /** 메뉴에서 세팅 버튼을 눌렀을 경우 */
                R.id.board_main_setting ->
                {
                    val act = activity as BoardMainActivity
                    act.fragmentController("board_setting", true,true)
                    true
                }

                else -> false
            }
        }


        /** Search view */
        boardMainFragmentBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
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
        val boardMainRecyclerAdapter = BoardMainRecyclerAdapter()
        boardMainFragmentBinding.boardMainRecycler.adapter = boardMainRecyclerAdapter

        boardMainFragmentBinding.boardMainRecycler.layoutManager = LinearLayoutManager(requireContext())

        // 가로 세로 구분자 설정
        // 1 --> 가로방향으로
        // 0 ---> 세로 방향으로
        boardMainFragmentBinding.boardMainRecycler.addItemDecoration(DividerItemDecoration(requireContext(), 1))


        /** 스크롤 동작할 때 실행 */
        boardMainFragmentBinding.boardMainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener()
            {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // 0 부터
                    val index1 = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()


                    // recycler view 가 관리하는 항목의 총 개수
                    val count1 = recyclerView.adapter?.itemCount



                    if(index1%10==0)
                    {
                        isPageUP=true
                    }


                    if(isPageUP&&!isPageMax)
                    {
                        if (index1 + 1 == count1)
                        {
                            // 같을 경우 page 를 +1 증가 시켜준다.
                            act.nowPage = act.nowPage + 1

                            isPageUP = false

                            getContentList(false)



                        }
                    }

                }
            })


        // 기져오게 설정하기
        getContentList(true)

        /** 새로 고침 기능 구현  ------------------------------------------------------------------------
         */
        boardMainFragmentBinding.boardMainSwipe.setOnRefreshListener {

            // recyclerView 에 있는 Content 를 비우고 새로 가져온다.
            getContentList(true)

            //계속해서 회전하지 않게 사용
            boardMainFragmentBinding.boardMainSwipe.isRefreshing = false
        }

        return boardMainFragmentBinding.root
    }


    /** RecyclerViewAdapter */
    inner class BoardMainRecyclerAdapter: RecyclerView.Adapter<BoardMainRecyclerAdapter.ViewHolderClass>()
    {


        /** ViewHolder Create */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass
        {
            val boardMainRecyclerItemBinding = BoardMainRecyclerviewBinding.inflate(layoutInflater)

            val holder = ViewHolderClass(boardMainRecyclerItemBinding)

            // 가로 , 세로
            val layoutParams = RecyclerView.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT)
            boardMainRecyclerItemBinding.root.layoutParams = layoutParams
            boardMainRecyclerItemBinding.root.setOnClickListener(holder)

            return holder
        }



        override fun onBindViewHolder(holder: ViewHolderClass, position: Int)
        {
            var safePosition = holder.adapterPosition
            // data 를 holder 에 넣기
            holder.boardMainItemNickname.text = contentWriterList[safePosition]

            holder.boardMainItemWriteDate.text= contentWriteDateList[safePosition]

            holder.boardMainItemSubject.text =contentSubjectList[safePosition]

            holder.boardContext.text = contentContext[safePosition]

            holder.boardMainItemSubject.isSelected = true




                holder.boardShowContext.setOnClickListener {
                    it.isClickable = true

                    holder.boardShowContext.visibility = View.GONE
                    holder.boardContext.visibility = View.VISIBLE
                    holder.boardClose.visibility = View.VISIBLE

                    holder.boardClose.setOnClickListener {

                        holder.boardShowContext.visibility = View.VISIBLE
                        holder.boardContext.visibility = View.GONE
                        holder.boardClose.visibility = View.GONE
                    }

                }




            /**  게시글에 이미지가 포함되어있을 때
             * */
            if(!contentImageUrl[safePosition].equals("empty"))
            {
                holder.boardImage.visibility = View.VISIBLE
                /** 이미지의 url 을 사용해서 SERVER 절대 경로에 있는 해당 이미지를 가져와서 구현 -------------
                 */
                thread{

                    val urlBuilder = FormBody.Builder()
                    urlBuilder.add("content_image_url",contentImageUrl[safePosition])


                    val uploadResponse = UseOkHttp().useThread("content/getImage",urlBuilder)


                    val result = uploadResponse.body?.bytes()!!

                    // 이미지 만들기
                    val bitmap = BitmapFactory.decodeByteArray(result,0, result.size)

                    activity?.runOnUiThread {
                        holder.boardImage.setImageBitmap(bitmap)
                    }
                }
            }
            else
            {
                holder.boardImage.visibility = View.GONE
            }

        }

        override fun getItemCount(): Int
        {
            return contentIdxList.size
        }
        /**
         *  ViewHolder Class--------------
         */
        inner class ViewHolderClass(boardMainRecyclerItemBinding: BoardMainRecyclerviewBinding)
            :RecyclerView.ViewHolder(boardMainRecyclerItemBinding.root), View.OnClickListener
        {

            val boardMainItemNickname = boardMainRecyclerItemBinding.boardMainItemNickname
            val boardMainItemSubject = boardMainRecyclerItemBinding.boardMainItemSubject
            val boardMainItemWriteDate = boardMainRecyclerItemBinding.boardMainItemWriteDate
            val boardImage = boardMainRecyclerItemBinding.mainImageView
            val boardShowContext = boardMainRecyclerItemBinding.showContext
            val boardContext = boardMainRecyclerItemBinding.contentsContentText
            val boardClose = boardMainRecyclerItemBinding.closeContext



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
        /** 들고오는 목록들을 초기화 해주는 method */
        if(clear)
        {
            contentIdxList.clear()
            contentWriterList.clear()
            contentSubjectList.clear()
            contentWriteDateList.clear()
            contentImageUrl.clear()
            contentContext.clear()


            val act = activity as BoardMainActivity

            act.nowPage = 1

            isPageUP = true

            isPageMax = false
        }

        thread{
            // Paging 하여 recycler view 목록들을 가져오는 method --------------

            val act = activity as BoardMainActivity

            val builder1 = FormBody.Builder()
            builder1.add("content_board_idx", "${act.selectedBoardType}")
            builder1.add("limit","${act.nowPage}")

            /** ContentsController --------------> bring: bringContent */
            val response = UseOkHttp().useThread("content/bring", builder1 )

            /** NetWorking Connection Success */
            if(response.isSuccessful)
            {
                val resultText = response.body?.string()!!.trim()

                val root = JSONArray(resultText)

                for(i in 0 until root.length())
                {
                    val obj = root.getJSONObject(i)

                        contentIdxList.add(obj.getInt("content_idx"))
                        contentWriterList.add(obj.getString("content_nick_name"))
                        contentWriteDateList.add(obj.getString("content_write_date"))
                        contentSubjectList.add(obj.getString("content_subject"))
                        contentImageUrl.add(obj.getString("content_image_url"))
                        contentContext.add(obj.getString("content_text"))
                }

                if(root.length() == 10)
                {
                    activity?.runOnUiThread {
                        Log.i("현재 page = ", act.nowPage.toString())
                        Toast.makeText(requireContext(), "Current Page ${act.nowPage}", Toast.LENGTH_SHORT).show()
                    }
                }


                // 마지막 페이지를 넘겼을 때 ,존재하지 않은 page 를 가져오려 하기 때문에
                // Page -1 을 해준다
                if(root.length() < 10 )
                {
                    // 빠질 것이 없다면 마지막 PAGE 로 고정

                    isPageUP = false

                    isPageMax = true


                    act.nowPage = act.nowPage -1


                    Log.i("Last Page", "This is last page")
                    activity?.runOnUiThread{
                        Toast.makeText(requireContext(), "This is Last Page", Toast.LENGTH_SHORT).show()
                    }


                }

               activity?.runOnUiThread {
                    // recyclerView 갱신
                    boardMainFragmentBinding.boardMainRecycler.adapter?.notifyDataSetChanged()
                }

            }
            /** NetWorking connect Fail*/
            else
            {
                activity?.runOnUiThread {
                    DialogEx().netWork(requireContext())
                }

            }

        }  // Paging 하여 recycler view 목록들을 가져오는 method --------------

    }

}


