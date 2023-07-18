package com.sing.dating.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sing.dating.R
import com.sing.dating.utils.FirebaseRef
import java.io.ByteArrayOutputStream

class JoinActivity : AppCompatActivity()
{
    private val Tag:String ="JoinActivity"

    private lateinit var auth: FirebaseAuth

    lateinit var profileImage : ImageView




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth


        profileImage = findViewById(R.id.join_profile_img)

        /**
         *  휴대폰에 내장되어 있는 Image 가져오기
         *
         */
        val getAction = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                uri->profileImage.setImageURI(uri)
            }
        )

        profileImage.setOnClickListener {
            getAction.launch("image/*")
        }





        val joinBtn = findViewById<Button>(R.id.join_joinBtn)

        joinBtn.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.join_email_area).text.toString()
            val pw = findViewById<TextInputEditText>(R.id.join_password_area).text.toString()

            Log.i(Tag, email)
            Log.i(Tag, pw)

            val pwCheck = findViewById<TextInputEditText>(R.id.join_pwCheck_area).text.toString()

            val gender = findViewById<TextInputEditText>(R.id.join_gender_area).text.toString()

            val age = findViewById<TextInputEditText>(R.id.join_age_area).text.toString()

            val location = findViewById<TextInputEditText>(R.id.join_location_area).text.toString()

            val nickname = findViewById<TextInputEditText>(R.id.join_nick_area).text.toString()




            /** real time database 에 data 추가
             */
            val userinfo = UserInfoDataSet(null,nickname,age,gender,location,email,pw)

            if(JoinValidCheck(userinfo,pwCheck))
            {
                auth.createUserWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.i(Tag, email)
                            Log.d(TAG, "createUserWithEmail:success")

                            val user = auth.currentUser
                            val uid = user?.uid.toString()

                            var userinfoFin = UserInfoDataSet(uid, nickname,age,gender,location,email,pw)


                                FirebaseRef.User.child(uid).setValue(userinfoFin)

                                uploadImage(uid)


                                startActivity(Intent(this, IntroActivity::class.java))
                                Toast.makeText(this, "Join Success", Toast.LENGTH_SHORT).show()



                        } else {
                            Log.w(TAG, "createUserWithEmail:failure")
                            Log.i(Tag, pw)
                        }
                    }
            }

        }



    }

    /** 사용자 마다 Profile 사진이 고유함으로
     */
    private fun uploadImage(uid: String)
    {

        val storage = Firebase.storage
        val storageRef = storage.reference.child(uid+".png")

        profileImage.isDrawingCacheEnabled = true
        profileImage.buildDrawingCache()
        val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = storageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    private fun JoinValidCheck(userinfo: UserInfoDataSet, pwCheck:String) :Boolean
    {
        var isValid : Boolean = true

        if(userinfo.email!!.isEmpty())
        {
            Toast.makeText(this,"Email is Empty", Toast.LENGTH_SHORT).show()
            isValid=false
            return isValid
        }

        if(userinfo.pw!!.isEmpty())
        {
            Toast.makeText(this,"password is Empty", Toast.LENGTH_SHORT).show()
            isValid=false
            return isValid
        }
        if(userinfo.age!!.isEmpty())
        {
            Toast.makeText(this,"age is Empty", Toast.LENGTH_SHORT).show()
            isValid=false
            return isValid
        }
        if(userinfo.location!!.isEmpty())
        {
            Toast.makeText(this,"location is Empty", Toast.LENGTH_SHORT).show()
            isValid=false
            return isValid
        }
        if(userinfo.gender!!.isEmpty())
        {
            Toast.makeText(this,"gender is Empty", Toast.LENGTH_SHORT).show()
            isValid=false
            return isValid

        }
        if(userinfo.nickname!!.isEmpty())
        {
            Toast.makeText(this,"nickname is Empty", Toast.LENGTH_SHORT).show()
            isValid=false
            return isValid
        }
        if(!pwCheck.equals(userinfo.pw))
        {
            Toast.makeText(this,"password and PasswordCheck \n not same each others", Toast.LENGTH_SHORT).show()
            isValid=false
            return isValid
        }
        return isValid
    }


}