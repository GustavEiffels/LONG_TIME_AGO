package com.sing.dating.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRef
{
    companion object
    {
        val database = Firebase.database

        val User = database.getReference("userInfo")
        val userLike = database.getReference("userLike")
    }
}