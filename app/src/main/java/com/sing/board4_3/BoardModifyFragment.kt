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
import androidx.core.content.FileProvider
import com.sing.board4_3.databinding.FragmentBoardModifyBinding
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread

class BoardModifyFragment : Fragment()
{

    // Binding
    lateinit var boardModifyFragmentBinding: FragmentBoardModifyBinding


    // uri
    lateinit var contentUri: Uri

    // image 를 담을 변수
    var uploadImage : Bitmap? =null



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


        /** modify , activity 불러오기
         */
        val act = activity as BoardMainActivity



        boardModifyFragmentBinding = FragmentBoardModifyBinding.inflate(inflater)

        boardModifyFragmentBinding.boardModifyToolbar.title="Contents Modify"

        /**
         *  Tool Bar-------------
         */
        // Toolbar 에 menu 적용
        boardModifyFragmentBinding.boardModifyToolbar.inflateMenu(R.menu.board_modify_menu)

        //toolBar Event 처리
        boardModifyFragmentBinding.boardModifyToolbar.setOnMenuItemClickListener {

            when(it.itemId)
            {
                R.id.board_modify_menu_camera ->{
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

                R.id.board_modify_menu_gallery ->{
                    val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    // 이미지만 가져오게 설정
                    albumIntent.type = "image/*"

                    val mimeType = arrayOf("image/*")
                    albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)

                    startActivityForResult(albumIntent, 2)

                    true
                }

                R.id.board_modify_menu_upload ->
                {

                    val boardModifySubject = boardModifyFragmentBinding.boardModifySubject.text.toString()

                    val boardModifyText = boardModifyFragmentBinding.boardModifyText.text.toString()

                    // 선택할 게시판의 index
                    val boardModifyType = act.boardIndexList[boardModifyFragmentBinding.boardModifyType.selectedItemPosition+1]

                    if(boardModifySubject.isNullOrEmpty())
                    {
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("Subject is Empty")
                        dialogBuilder.setMessage("Subject is Empty Please fill in the blank to something")
                        dialogBuilder.setPositiveButton("confirm"){
                                dialogInterface: DialogInterface, i: Int ->
                            boardModifyFragmentBinding.boardModifySubject.requestFocus()

                        }
                        dialogBuilder.show()
                        return@setOnMenuItemClickListener true
                    }


                    if( boardModifyText.isNullOrEmpty() )
                    {
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("Contents is Empty")
                        dialogBuilder.setMessage("Contents is Empty Please fill in the blank to something")
                        dialogBuilder.setPositiveButton("confirm"){
                                dialogInterface: DialogInterface, i: Int ->
                            boardModifyFragmentBinding.boardModifyText.requestFocus()

                        }
                        dialogBuilder.show()
                        return@setOnMenuItemClickListener true
                    }

                    thread{
                        val client = OkHttpClient()

                        val site = "http://${ServerIP.serverIp}/modify/modify"


                        val builder1 = MultipartBody.Builder()
                        builder1.addFormDataPart("content_board_idx","$boardModifyType")
                        builder1.addFormDataPart("content_subject",boardModifySubject)
                        builder1.addFormDataPart("content_text",boardModifyText)
                        builder1.addFormDataPart("content_idx","${act.readContentidx}")

                        Log.i("boardModifiyType = {} ","$boardModifyType" )

                        var file: File? = null


                        // 이미지가 존재할 때만
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

                        val request = Request.Builder().url(site).patch(formBody).build()

                        val response = client.newCall(request).execute()


                        // 통신이 성공적으로 완료 되면
                        if(response.isSuccessful)
                        {

                            //                          저장된 글 번호 받아오기
                            val resultText = response.body?.string()!!.trim()

                            act.readContentidx = Integer.parseInt(resultText)

                            // 제일 최신 글
                            act.lastIdx = Integer.parseInt(resultText)


                            activity?.runOnUiThread {
                                // keypad 내리기
                                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                inputMethodManager.hideSoftInputFromWindow(boardModifyFragmentBinding.boardModifySubject.windowToken, 0)
                                inputMethodManager.hideSoftInputFromWindow(boardModifyFragmentBinding.boardModifyText.windowToken,0 )


                                val dialogBuilder = AlertDialog.Builder(requireContext())
                                dialogBuilder.setTitle("Modify Complete")
                                dialogBuilder.setMessage("Content Modify success")
                                dialogBuilder.setPositiveButton("confirm"){
                                    dialogInterface: DialogInterface, i: Int->

                                    //backStack 에서 제거
                                    act.fragmentRemoveBackStack("board_modify")
                                    act.fragmentController("board_read", true,true)
                                }
                                dialogBuilder.show()
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

                    true
                }
             else->false
            }
        }

        thread{
            val client = OkHttpClient()

            val site = "http://${ServerIP.serverIp}/modify/getContent"

            val builder1 = FormBody.Builder()

            // 현재 선택한 Content 의 번호
            builder1.add("read_content_idx","${act.readContentidx}")

            val formBody= builder1.build()

            val request = Request.Builder().url(site).post(formBody).build()

            val response = client.newCall(request).execute()

            if(response.isSuccessful)
            {
                val resultText = response.body?.string()!!.trim()

                val obj = JSONObject(resultText)

                // activity 위에서 Thread 수행
                activity?.runOnUiThread {
                    boardModifyFragmentBinding.boardModifySubject.setText(obj.getString("content_subject"))
                    boardModifyFragmentBinding.boardModifyText.setText(obj.getString("content_text"))

                    // 이미지 이름 가져오기
                    val contentImage = obj.getString("content_image")

                    // Image 절대값 가져오기
                    val contentImageAbsolute = obj.getString("content_image_url")

                    if(contentImage=="")
                    {
                        boardModifyFragmentBinding.boardModifyImage.visibility = View.GONE
                    }
                    else
                    {
                        thread{

                            val act = activity as BoardMainActivity

                            val uploadClient = OkHttpClient()

                            val uploadSite = "http://${ServerIP.serverIp}/content/getImage"

                            val urlBuilder = FormBody.Builder()
                            urlBuilder.add("content_image_url",contentImageAbsolute)

                            val urlForm = urlBuilder.build()

                            val urlRequest = Request.Builder().url(uploadSite).post(urlForm).build()

                            val uploadResponse = uploadClient.newCall(urlRequest).execute()

                            val result = uploadResponse.body?.bytes()!!

                            // 이미지 만들기
                            val bitmap = BitmapFactory.decodeByteArray(result,0, result.size)

                            activity?.runOnUiThread {
                                boardModifyFragmentBinding.boardModifyImage.setImageBitmap(bitmap)
                            }
                        }
                    }

                    /**
                     *  Spinner 구성하기 -------------------
                     *
                     *  act.boardNameList
                     *  board 이름을 spinner 안에 넣는다 , 첫번째는 전체게시판임으로 1을 drop 시킨다.
                     */
                    val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
                        act.boardNameList.drop(1))

                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    boardModifyFragmentBinding.boardModifyType.adapter = spinnerAdapter

                    boardModifyFragmentBinding.boardModifyType.setSelection(obj.getInt("content_board_idx") - 1)
                }
            }
        }

        return boardModifyFragmentBinding.root
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

                    boardModifyFragmentBinding.boardModifyImage.setImageBitmap(uploadImage)
                    // 이미지가 없을경우 수정하면 보여야함으로 보이게 설정
                    boardModifyFragmentBinding.boardModifyImage.visibility = View.VISIBLE

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

                            boardModifyFragmentBinding.boardModifyImage.setImageBitmap(uploadImage)
                            boardModifyFragmentBinding.boardModifyImage.visibility = View.VISIBLE

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
                                boardModifyFragmentBinding.boardModifyImage.setImageBitmap(uploadImage)
                                boardModifyFragmentBinding.boardModifyImage.visibility = View.VISIBLE

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