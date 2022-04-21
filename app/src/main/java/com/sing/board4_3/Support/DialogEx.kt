package com.sing.board4_3.Support

import android.app.AlertDialog
import android.content.Context

class DialogEx
{

    fun netWork(context:Context)
    {
            val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setTitle("NetWork Error")
    dialogBuilder.setMessage("It's something Wrong Try Again")
    dialogBuilder.setPositiveButton("confirm",null)
    dialogBuilder.show()
    }


    fun makeDialog(context: Context, setTitle:String, setMessage:String, setPositiveButton:String)
    {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(setTitle)
        dialogBuilder.setMessage(setMessage)
        dialogBuilder.setPositiveButton(setPositiveButton,null)
        dialogBuilder.show()
    }

    fun makeDialogNoPositiveButton(context: Context, setTitle:String, setMessage:String)
    {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(setTitle)
        dialogBuilder.setMessage(setMessage)
        dialogBuilder.show()
    }
}