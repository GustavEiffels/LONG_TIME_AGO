package com.sing.board4_3.BoardFragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sing.board4_3.Activity.BoardMainActivity
import com.sing.board4_3.R
import com.sing.board4_3.Support.DialogEx
import com.sing.board4_3.Support.UseOkHttp
import com.sing.board4_3.databinding.FragmentBoardReadBinding
import okhttp3.FormBody
import org.json.JSONObject
import kotlin.concurrent.thread

class ReadFragment : Fragment() {

    /** Binding 설정 -------------------------------
     */
    lateinit var binding: FragmentBoardReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // binding
        binding = FragmentBoardReadBinding.inflate(layoutInflater)
        binding.boardReadToolbar.title="Board Read"

        /** Toolbar 상단에 backButton 추가로 생성 ---------------------------------------------------- */
        val navIcon = requireActivity().getDrawable(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

        binding.boardReadToolbar.navigationIcon = navIcon


        // 색상을 변경 --- android 10 버전일 경우와 그렇지 않을 경우 둘다 고려
        /** 현재 SDK Version 이 10 Version 이상인 경우 ----------------------------------------------- */
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            binding.
            boardReadToolbar.
            navigationIcon?.
            colorFilter= BlendModeColorFilter(Color.parseColor("#FFFFFF"), BlendMode.SRC_ATOP)
        }


        /** 현재 SDK Version 이 10 Version 미만인 경우 ----------------------------------------------- */
        else
        {
            binding.boardReadToolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"),PorterDuff.Mode.SRC_ATOP
            )
        }

        /** 화살표를 눌렀을 경우 현재 Fragment 를 backStack 에서 제거 --------------------------------------*/
        binding.boardReadToolbar.setNavigationOnClickListener {
            val act = activity as BoardMainActivity
            act.fragmentRemoveBackStack("board_read")
        }


        /** 게시글을 읽기 위한 thread -------------------------------------------------------------------*/
        thread{
        // Thread 1 ------

            /** ContentsController */
            val act = activity as BoardMainActivity
            val builder1 = FormBody.Builder()
            builder1.add("read_content_idx", "${act.readContentidx}")


            val response = UseOkHttp().useThread("content/read",builder1)




            /** Success */
            if(response.isSuccessful)
            { // response.isSuccessful 1 ---------

                /** 게시글 정보 가져오기 */
                val resultText = response.body?.string()!!.trim()

                // json 형태로 변형
                val obj = JSONObject(resultText)


                // 작성자 index 를 얻기 위한 변수 선언
                val contentWriterIdx = obj.getInt("content_writer_idx")

                activity?.runOnUiThread {
                    // activity?.runOnUiThread 1 -----------

                    binding.boardReadSubject.text = obj.getString("content_subject")
                    binding.boardReadWriter.text = obj.getString("content_nick_name")
                    binding.boardReadWriteDate.text = obj.getString("content_write_date")
                    binding.boardReadText.text = obj.getString("content_text")


                    // Image 가져오기
                    val contentImage = obj.getString("content_image_url")


                    /** 게시글의 이미지 존재 X  */
                    if( contentImage == "")
                    {
                        binding.boardReadImage.visibility =View.GONE
                    }

                    /** 게시글의 이미지 존재 */
                    else
                    { // 게시글 이미지 존재 -----------

                        /** 이미지 url 의 절대경로를 사용해서 image 구현  */
                        thread{
                            // thread 2 ----------


                            val urlBuilder = FormBody.Builder()
                            urlBuilder.add("content_image_url",contentImage)

                            /** ContentsController ----------> getImage */
                            val uploadResponse = UseOkHttp().useThread("content/getImage",urlBuilder)

                            val result = uploadResponse.body?.bytes()!!

                            /** 이미지 만들기 */
                            val bitmap = BitmapFactory.decodeByteArray(result,0, result.size)

                                activity?.runOnUiThread {
                                    binding.boardReadImage.setImageBitmap(bitmap)
                                }

                            } // thread 2 ----------

                    }// 게시글 이미지 존재 -----------

                    /** 로그인한 사용자의 index 를 받는다  */
                    val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)


                    val loginUserIdx = pref.getInt("login_user_idx",-1)


                    /** 게시글 작성자와 로그인 user 가 같은 경우 menu 가시화  */

                    if( loginUserIdx.equals(contentWriterIdx) )
                    {

                        /** ToolBar Menu 설정 */

                        // Toolbar 에 menu 추가
                        binding.boardReadToolbar.inflateMenu(R.menu.board_read_menu)

                        // Toolbar 에 menu setting
                        binding.boardReadToolbar.setOnMenuItemClickListener{
                            // boardReadToolbar.setOnMenuItemClickListener ----------

                            when(it.itemId)

                            { // when(it.itemId) ----------

                                R.id.board_read_menu_modify ->
                                {
                                    val act =activity as BoardMainActivity

                                    // backStack 에 추가 및 , Animation 추가
                                    act.fragmentController("board_modify", true, true)
                                    true
                                }

                                /** 게시글 삭제 메뉴 */
                                R.id.board_read_menu_delete ->
                                { // board_read_menu_delete ---------

                                    thread{
                                        // thread 3 --------------

                                        val act = activity as BoardMainActivity


                                        val builder1 = FormBody.Builder()
                                        builder1.add("content_idx", "${act.readContentidx}")


                                        val response = UseOkHttp().useThreadDelete("content/delete",builder1)


                                        /** Success */
                                        if(response.isSuccessful)
                                        { // Success ------------

                                            activity?.runOnUiThread {
                                                // runOnUiThread 2 ----------

                                                /** 게시글을 삭제하고 board_read 로 이동 */

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
                                            } // runOnUiThread 2 ----------


                                        } // Success ------------

                                        /** NetWork Connection Error*/
                                        else
                                        {
                                            activity?.runOnUiThread{
                                                DialogEx().netWork(requireContext())
                                            }
                                        }


                                    } // thread 3 --------------

                                    true

                                } // board_read_menu_delete ---------

                                else -> false

                            }  // when(it.itemId) ----------

                        } // boardReadToolbar.setOnMenuItemClickListener ----------

                    } // loginUserIdx.equals(contentWriterIdx) ----------

                    } // activity?.runOnUiThread 1 -----------

                } // response.isSuccessful 1 ---------

            }// Thread 1 ------

        return binding.root
    }

}