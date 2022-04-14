package com.sing.board4_3

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sing.board4_3.databinding.FragmentBoardReadBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.concurrent.thread

class BoardReadFragment : Fragment() {

    /** Binding 설정 -------------------------------
     */
    lateinit var boardReadFragmentBinding: FragmentBoardReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        boardReadFragmentBinding = FragmentBoardReadBinding.inflate(layoutInflater)
        boardReadFragmentBinding.boardReadToolbar.title="Board Read"

        // drawable 객체 생성
        val navIcon = requireActivity().getDrawable(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

        boardReadFragmentBinding.boardReadToolbar.navigationIcon = navIcon

        // 색상을 변경 --- android 10 버전일 경우와 그렇지 않을경우 둘다 고려


        // Q
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            boardReadFragmentBinding.
            boardReadToolbar.
            navigationIcon?.
            colorFilter= BlendModeColorFilter(Color.parseColor("#FFFFFF"), BlendMode.SRC_ATOP)
        }
        else
        {
            boardReadFragmentBinding.boardReadToolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"),PorterDuff.Mode.SRC_ATOP
            )
        }

        boardReadFragmentBinding.boardReadToolbar.setNavigationOnClickListener {
            val act = activity as BoardMainActivity
            act.fragmentRemoveBackStack("board_read")
        }




        thread{
            // Server 로 부터 데이터 받아오기
            val client = OkHttpClient()

            val site = "http://${ServerIP.serverIp}/content/readContent"

            val act = activity as BoardMainActivity

            val builder1 = FormBody.Builder()
            builder1.add("read_content_idx", "${act.readContentidx}")

            val formbody = builder1.build();

            val request = Request.Builder().url(site).post(formbody).build()

            val response = client.newCall(request).execute()



            if(response.isSuccessful)
            {
                val resultText = response.body?.string()!!.trim()
                // json 형태로 변형
                val obj = JSONObject(resultText)


                // 작성자 index 를 얻기 위한 변수 선언
                val contentWriterIdx = obj.getInt("content_writer_idx")

                activity?.runOnUiThread {
                    boardReadFragmentBinding.boardReadSubject.text = obj.getString("content_subject")
                    boardReadFragmentBinding.boardReadWriter.text = obj.getString("content_nick_name")
                    boardReadFragmentBinding.boardReadWriteDate.text = obj.getString("content_write_date")
                    boardReadFragmentBinding.boardReadText.text = obj.getString("content_text")




                    // Image 가져오기
                    val contentImage = obj.getString("content_image_url")

                    // 존재하지 않는다면
                    if( contentImage == "")
                    {
                        // 안보이게 설정
                        boardReadFragmentBinding.boardReadImage.visibility =View.GONE
                    }
                    else
                    {

                        thread{

//                            val act = activity as BoardMainActivity

                            val uploadClient = OkHttpClient()

                            val uploadSite = "http://${ServerIP.serverIp}/content/getImage"

                            val urlBuilder = FormBody.Builder()
                            urlBuilder.add("content_image_url",contentImage)

                            val urlForm = urlBuilder.build()

                            val urlRequest = Request.Builder().url(uploadSite).post(urlForm).build()

                            val uploadResponse = uploadClient.newCall(urlRequest).execute()

                            val result = uploadResponse.body?.bytes()!!

                            // 이미지 만들기
                            val bitmap = BitmapFactory.decodeByteArray(result,0, result.size)

                                activity?.runOnUiThread {
                                    boardReadFragmentBinding.boardReadImage.setImageBitmap(bitmap)
                                }
                            }
                    }

                    // 로그인한 사용자 index 번호 추출
                    val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

                    // 초기값은 -1
                    val loginUserIdx = pref.getInt("login_user_idx",-1)

                    // 로그인 index 와 글쓴이의 index 가 같을  경우
                    if( loginUserIdx.equals(contentWriterIdx) )
                    {

                        /** ToolBar Menu 설정 ----------------------------------------------------------------------
                         */

                        // Toolbar 에 menu 추가
                        boardReadFragmentBinding.boardReadToolbar.inflateMenu(R.menu.board_read_menu)

                        // Toolbar 에 menu 눌렀을 때 적용하기
                        boardReadFragmentBinding.boardReadToolbar.setOnMenuItemClickListener {
                            when(it.itemId)
                            {
                                // 수정할 때
                                R.id.board_read_menu_modify->
                                {
                                    val act =activity as BoardMainActivity
                                    // backStack 에 추가 및 , Animation 추가
                                    act.fragmentController("board_modify", true, true)
                                    true
                                }

                                // 삭제할 때 method 구현
                                R.id.board_read_menu_delete->
                                {

                                    thread{

                                        val act = activity as BoardMainActivity

                                        val client = OkHttpClient()

                                        val site = "http://${ServerIP.serverIp}/content/delete"

                                        val builder1 = FormBody.Builder()

                                        builder1.add("content_idx", "${act.readContentidx}")

                                        val formBody = builder1.build()

                                        val request = Request.Builder().url(site).delete(formBody).build()

                                        Log.d("test","leng")

                                        val response = client.newCall(request).execute()


                                        // 통신이 정상적으로 되었다면
                                        if(response.isSuccessful)
                                        {
                                            activity?.runOnUiThread {
                                                val dialogBuilder = AlertDialog.Builder(requireContext())
                                                dialogBuilder.setTitle("Content Delete")
                                                dialogBuilder.setMessage("This Content id deleted perfectly")
                                                dialogBuilder.setPositiveButton("confirm"){
                                                    // board_read Fragment 를 BackStack 에서 제거
                                                        dialogInterface: DialogInterface, i: Int ->
                                                    val act = activity as BoardMainActivity
                                                    act.fragmentRemoveBackStack("board_read")
                                                }
                                                dialogBuilder.show()
                                            }


                                        }
                                        else
                                        {
                                            activity?.runOnUiThread{

                                                val dialogBuilder = AlertDialog.Builder(requireContext())
                                                dialogBuilder.setTitle("NetWork Error")
                                                dialogBuilder.setMessage("NetWork Error Emerge")
                                                dialogBuilder.setPositiveButton("confirm",null)
                                                dialogBuilder.show()

                                            }
                                        }
                                    }

                                    true
                                }
                                else -> false
                            }

                        }
                    }


                    }

                }
            }
        return boardReadFragmentBinding.root
    }

}