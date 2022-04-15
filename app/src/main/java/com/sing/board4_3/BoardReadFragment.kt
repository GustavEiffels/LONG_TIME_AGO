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

        /** Toolbar 상단에 backButton 추가로 생성 -------------------------------------------------------
         */
        val navIcon = requireActivity().getDrawable(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

        boardReadFragmentBinding.boardReadToolbar.navigationIcon = navIcon


        // 색상을 변경 --- android 10 버전일 경우와 그렇지 않을경우 둘다 고려

        /** 현재 SDK Version 이 10 Version 이상인 경우 --------------------------------------------------
         */
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            boardReadFragmentBinding.
            boardReadToolbar.
            navigationIcon?.
            colorFilter= BlendModeColorFilter(Color.parseColor("#FFFFFF"), BlendMode.SRC_ATOP)
        }


        /** 현재 SDK Version 이 10 Version 미만인 경우 --------------------------------------------------
         */
        else
        {
            boardReadFragmentBinding.boardReadToolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"),PorterDuff.Mode.SRC_ATOP
            )
        }

        /** 화살표를 눌렀을 경우 현재 Fragment 를 backStack 에서 제거 ----------------------------------------
         */
        boardReadFragmentBinding.boardReadToolbar.setNavigationOnClickListener {
            val act = activity as BoardMainActivity
            act.fragmentRemoveBackStack("board_read")
        }


        /** page를 읽기 위한 thread -------------------------------------------------------------------
         */
        thread{
            // Server 로 부터 데이터 받아오기
            val client = OkHttpClient()

            val site = "http://${ServerIP.serverIp}/content/read"

            val act = activity as BoardMainActivity

            val builder1 = FormBody.Builder()
            builder1.add("read_content_idx", "${act.readContentidx}")

            val formbody = builder1.build();

            val request = Request.Builder().url(site).post(formbody).build()

            val response = client.newCall(request).execute()


            /** 읽기에 성공 했을 경우 JSON 으로 게시글에 대한 정보를 받는다 --------------------------------------
             */
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


                    /** 게시글의 이미지가 존재하지 않는 경우 -------------------------------------------------
                     */
                    if( contentImage == "")
                    {
                        // 보이지 않게 설정
                        boardReadFragmentBinding.boardReadImage.visibility =View.GONE
                    }

                    /** 게시글의 이미지가 존재하는 경우 -----------------------------------------------------
                     */
                    else
                    {

                        /** 이미지의 url 을 사용해서 SERVER 절대 경로에 있는 해당 이미지를 가져와서 구현 -------------
                         */
                        thread{
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

                    /** 로그인한 사용자의 index 를 받는다 -------------------------------------------------
                     */
                    val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)

                    // 초기값은 -1
                    val loginUserIdx = pref.getInt("login_user_idx",-1)


                    /** 해당 Content 의 작성자와 로그인한 사용자가 같을 경우 menu가 보여지게 만듬 ------------------
                     */
                    if( loginUserIdx.equals(contentWriterIdx) )
                    {

                        /** ToolBar Menu 설정 --------------------------------------------------------
                         */

                        // Toolbar 에 menu 추가
                        boardReadFragmentBinding.boardReadToolbar.inflateMenu(R.menu.board_read_menu)

                        // Toolbar 에 menu 상호작용
                        boardReadFragmentBinding.boardReadToolbar.setOnMenuItemClickListener{
                            when(it.itemId)
                            {
                                /** 해당 Content 를 수정하기 위한 Fragment로 이동하는 기능 -------------------
                                 */
                                R.id.board_read_menu_modify ->
                                {
                                    val act =activity as BoardMainActivity
                                    // backStack 에 추가 및 , Animation 추가
                                    act.fragmentController("board_modify", true, true)
                                    true
                                }

                                /** 해당 Content 를 삭제 기능 ------------------------------------------
                                 */
                                R.id.board_read_menu_delete ->
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