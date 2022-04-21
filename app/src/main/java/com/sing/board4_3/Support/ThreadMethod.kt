package com.sing.board4_3.Support

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class ThreadMethod
{


    fun useThread(url:String, formBody: FormBody.Builder)
    : Response {
        val client = OkHttpClient()

        val site = "http://${ServerIP.serverIp}/${url}"

        var form = formBody.build()

        val request = Request.Builder().url(site).post(form).build()

        val response = client.newCall(request).execute()

        return response
    }
}