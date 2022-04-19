package com.sing.board4_3

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import com.sing.board4_3.databinding.FragmentBoardWriteBinding
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread

class BoardWriteFragment : Fragment() {

    //Binding 설정
    lateinit var boardWriteFragmentBinding: FragmentBoardWriteBinding



    /**
     *  저장할 uri
     */
    lateinit var contentUri : Uri


    /** image
     */
    var uploadImage: Bitmap? =null

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


        /**  Board Main Activity 가져오기
         */
        val act = activity as BoardMainActivity


        boardWriteFragmentBinding = FragmentBoardWriteBinding.inflate(inflater)
        boardWriteFragmentBinding.boardWriteToolbar.title="Board Writing"

        /** toolbar setting
         */
        boardWriteFragmentBinding.boardWriteToolbar.inflateMenu(R.menu.board_write_menu)

        // toolbar 에서 click이 감지 되었을 때
        boardWriteFragmentBinding.boardWriteToolbar.setOnMenuItemClickListener {
            when(it.itemId)
            {
                // itemid 가 camera 일 때
                R.id.board_write_menu_camera ->
                {

                    val filePath = requireContext().getExternalFilesDir(null).toString()

                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    // 촬영한 사진이 저장될 파일 이름

                    val fileName = "/temp_${System.currentTimeMillis()}.jpg"
                    val picPath = "$filePath/$fileName"

                    val file = File(picPath)

                    contentUri = FileProvider
                        .getUriForFile(requireContext(),
                            "com.sing.board4_3.camera.file_provider",
                        file)

                    if(contentUri != null)
                    {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                        startActivityForResult(cameraIntent, 1)
                    }


                    true
                }
                // itemid 가 gallery
                R.id.board_write_menu_gallery ->
                {
                    val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    // 이미지만 가져오게 설정
                    albumIntent.type = "image/*"

                    val mimeType = arrayOf("image/*")
                    albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)

                    startActivityForResult(albumIntent, 2)

                    true
                }

                // itemid 가 upload
                R.id.board_write_menu_upload ->
                {//R.id.board_write_menu_upload ->


                    val act  = activity as BoardMainActivity
                    // 글을 작성한 fragment 가 backstack 에서 빠져야한다.
                    // 그렇게 하면 뒤로가기 했을 때, 바로 글 목록으로 이동이 가능하다 .


                    /** ---------------- 게시글 작성
                     */
                    val boardWriteSubject = boardWriteFragmentBinding.boardWriteSubject.text.toString()

                    val boardWriteText = boardWriteFragmentBinding.boardWriteText.text.toString()


                    /** 게시판 글의 타입과 게시판 작성자의 정보 받아오기 ----------------------------------------
                     */
                    // 게시판 index 번호
                    val boardWriteType = act.boardIndexList[boardWriteFragmentBinding.boardWriteType.selectedItemPosition + 1]

                    val pref = requireContext().getSharedPreferences("login_data", Context.MODE_PRIVATE)
                    val boardWriteIdx = pref.getInt("login_user_idx",0)



                    if( boardWriteSubject==null || boardWriteSubject.length == 0)
                    {

                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("Title Input Error")
                        dialogBuilder.setMessage("Please Setting title!")
                        dialogBuilder.setPositiveButton("confirm"){
                                dialogInterface: DialogInterface, i: Int ->
                            boardWriteFragmentBinding.boardWriteSubject.requestFocus()
                        }
                        dialogBuilder.show()
                        return@setOnMenuItemClickListener true
                    }

                    if( boardWriteText==null || boardWriteText.length == 0 )
                    {
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("Contents Input Error")
                        dialogBuilder.setMessage("Please make some contents")
                        dialogBuilder.setPositiveButton("confirm"){
                                dialogInterface: DialogInterface, i: Int ->
                            boardWriteFragmentBinding.boardWriteText.requestFocus()
                        }
                        dialogBuilder.show()
                        return@setOnMenuItemClickListener true
                    }

                    /** Contents DB 에 추가하기 -----------------------------------------------------*/
                    thread{

                        val client = OkHttpClient()

                        val url = "http://${ServerIP.serverIp}/content"

                        val act = activity as BoardMainActivity

                        val builder1 = MultipartBody.Builder()

                        builder1.addFormDataPart("content_board_idx","$boardWriteType")
                        builder1.addFormDataPart("content_writer_idx","$boardWriteIdx")
                        builder1.addFormDataPart("content_subject",boardWriteSubject)
                        builder1.addFormDataPart("content_text",boardWriteText)

                        /** file upload 설정 -----
                         */
                        var file:File? = null

                        // 선택한 이미지가 있다면
                        if(uploadImage != null)
                        {
                            val filePath = requireContext().getExternalFilesDir(null).toString()
                            val fileName = "/temp_${System.currentTimeMillis()}.jpg"
                            val picPath = "$filePath/$fileName"

                            // 파일을 임시 폴더에 저장
                            file = File(picPath)
                            val fos = FileOutputStream(file)
                            uploadImage?.compress(Bitmap.CompressFormat.JPEG, 100, fos)

                            builder1.addFormDataPart("content_image", file.name, file.asRequestBody(MultipartBody.FORM))
                        }
                        val formBody = builder1.build()

                        val request = Request.Builder().url(url).post(formBody).build()

                        val response = client.newCall(request).execute()


                        if(response.isSuccessful)
                        {

//                          저장된 글 번호 받아오기
                            val resultText = response.body?.string()!!.trim()

                            act.readContentidx = Integer.parseInt(resultText)

                            // 제일 최신 글
                            act.lastIdx = Integer.parseInt(resultText)

                            Log.d("test", "${act.readContentidx}")


                            activity?.runOnUiThread {

                                /** keyboard 내려주기
                                 */
                                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                inputMethodManager.hideSoftInputFromWindow(boardWriteFragmentBinding.boardWriteSubject.windowToken, 0)
                                inputMethodManager.hideSoftInputFromWindow(boardWriteFragmentBinding.boardWriteText.windowToken, 0)

                                Toast.makeText(requireContext(), "Write Complete", Toast.LENGTH_SHORT).show()
                                act.fragmentRemoveBackStack("board_write")
                                act.fragmentController("board_read", true,true)
                            }
                        }
                        /**
                         *  통신실패
                         */
                        else
                        {
                         activity?.runOnUiThread{
                             val dialogBuilder = AlertDialog.Builder(requireContext())
                             dialogBuilder.setTitle("Error")
                             dialogBuilder.setMessage("Write Error Emerge")
                             dialogBuilder.setPositiveButton("confirm", null)
                             dialogBuilder.show()
                         }
                        }

                    }
                    /**
                     *  upload 를 누르면 backStack 에서 baord_write 는 제거
                     *
                     *  board_read 는 추가하여 바로 이동하게 만든다.
                     */
                    true

                }
                else -> false
            }
        }


        //Spinner 구성
        // drop ( 전체 게시판을 drop 하고 나서 가져온다 )
        val spinnerAdapter = ArrayAdapter(requireContext(),
                                            android.R.layout.simple_spinner_item, act.boardNameList.drop(1))

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Spinner setting --- > boardWriteType : spinner id
        boardWriteFragmentBinding.boardWriteType.adapter = spinnerAdapter

        // 게시판 고르기

        // act.selectedBoardType == 0 ---> 전체 게시판
        if(act.selectedBoardType == 0)
        {
            boardWriteFragmentBinding.boardWriteType.setSelection(0)
        }
        else
        {

            boardWriteFragmentBinding.boardWriteType.setSelection(act.selectedBoardType -1 )

        }
        return boardWriteFragmentBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {
            1->
            {
                if(resultCode == Activity.RESULT_OK)

                {
                    uploadImage = BitmapFactory.decodeFile(contentUri.path)


                    val act = activity as BoardMainActivity

                    act.uploadImage = uploadImage

                    boardWriteFragmentBinding.boardWriteImage.setImageBitmap(uploadImage)

                    val file = File(contentUri.path)
                    file.delete()
                }

            }

            2->
            {
                if(resultCode == Activity.RESULT_OK)
                {
                    // 선택할 수 있는 이미지에 접근할 수 있는 URI

                    val uri = data?.data
                    if(uri != null )
                    {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        {
                            val source = ImageDecoder.createSource(activity?.contentResolver!!, uri)
                            uploadImage = ImageDecoder.decodeBitmap(source)

                            boardWriteFragmentBinding.boardWriteImage.setImageBitmap(uploadImage)

                            val act = activity as BoardMainActivity

                            act.uploadImage = uploadImage

                        }
                        // 9 버전 이하는 cursor 를 이용해야 한다 .
                        else
                        {
                            val cursor = activity?.contentResolver?.query(uri, null, null, null, null )
                            if(cursor != null)
                            {
                                cursor.moveToNext()

                                val index = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                                val source = cursor.getString(index)
                                uploadImage = BitmapFactory.decodeFile(source)
                                boardWriteFragmentBinding.boardWriteImage.setImageBitmap(uploadImage)

                                val act = activity as BoardMainActivity

                                act.uploadImage = uploadImage
                            }
                        }
                    }
                }
            }

        }
    }
}