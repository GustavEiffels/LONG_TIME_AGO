package com.sing.board4_3

import android.Manifest
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sing.board4_3.databinding.ActivityBoardMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import kotlin.concurrent.thread

class BoardMainActivity : AppCompatActivity() {
    // Binding
    lateinit var boardMainActivityBinding: ActivityBoardMainBinding

    // 변수선언
    lateinit var currentFragment: Fragment

    // Json 객체에서 구한 값 저장하는 list
    val boardNameList = ArrayList<String>()
    val boardIndexList = ArrayList<Int>()

    // 제일 최근에 등록된 게시글
    var lastIdx = 0


    // User 가 가지고 있는 게시판의 정보
    var selectedBoardType = 0


    // 권한 설정할 리스트
     val permissionList  = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION

     )

    // 현재 읽고 있는 글의 번호
    var readContentidx = 0

    var uploadImage: Bitmap? =null


    // 현재 보고 있는 page 번호
    var nowPage = 1


    // 검색어 저장
    var query = ""

    var resignOrChange=""


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        boardMainActivityBinding = ActivityBoardMainBinding.inflate(layoutInflater)

        setContentView(boardMainActivityBinding.root)


        // 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionList, 0)
        }

        boardNameList.add("전체 게시판")
        boardIndexList.add(0)

        thread{
            val client = OkHttpClient()

            val site = "http://${ServerIP.serverIp}/board"

            val request = Request.Builder().url(site).get().build()

            val response = client.newCall(request).execute()

            if(response.isSuccessful)
            {
                val result_text = response.body?.string()!!.trim()

                val root = JSONArray(result_text)

                for(i in 0 until root.length())
                {
                    val obj = root.getJSONObject(i);

                    val boardIdx = obj.getInt("board_idx")
                    val boardName = obj.getString("board_name")

                    boardIndexList.add(boardIdx)
                    boardNameList.add(boardName)
                }
            }


        }


        fragmentController("board_main", false ,false)
    }

    /**
     *  fragment Controller ------------------------
     */
    fun fragmentController(name:String , add:Boolean, animate:Boolean)
    {
        when(name)
        {

            "board_main" ->
            {
             currentFragment= BoardMainFragment()
            }

            // name 이 board_read 일 때 BoardReadFragment 실행
            "board_read"->
            {
                currentFragment= BoardReadFragment()
            }

            "board_write"->
            {
                currentFragment= BoardWriteFragment()
            }

            "board_modify"->
            {
                currentFragment= BoardModifyFragment()
            }
            "board_setting"->
            {
                currentFragment= BoardSettingFragment()
            }
            "password_confirm"->
            {
                resignOrChange  = "change"
                currentFragment= PassWordConfirmFragment()
            }
            "change_password"->
            {
                currentFragment= ChangePasswordFragment()
            }
            "search_result"->
            {
                currentFragment=SearchResultFragment()
            }
            "password_resign"->
            {   resignOrChange = "resign"
                currentFragment= PassWordConfirmFragment()
            }
            "resign"->
            {
                currentFragment= ResignFragment()
            }

        }
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.board_main_container,currentFragment)



        if(add)
        {
            // 안전하게 backStack 에서 Fragment를 지우기 위해서
            trans.addToBackStack(name)
        }

        if(animate)
        {
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        trans.commit()
    }


    /**
     *  이름을 받아서 Fragment 를 제거하는 method
     */
    fun fragmentRemoveBackStack(name:String)
    {
        // FragmentManager.POP_BACK_STACK_INCLUSIVE --> Fragment 가 제거되게 설정
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}