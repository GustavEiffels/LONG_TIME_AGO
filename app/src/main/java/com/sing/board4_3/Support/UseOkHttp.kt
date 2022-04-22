package com.sing.board4_3.Support

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class UseOkHttp
{


    // Post
    fun useThread(url:String, formBody: FormBody.Builder)
    : Response {
        val client = OkHttpClient()

        val site = "http://${ServerIP.serverIp}/${url}"

        var form = formBody.build()

        val request = Request.Builder().url(site).post(form).build()

        val response = client.newCall(request).execute()

        return response
    }

    // Delete
    fun useThreadDelete(url:String, formBody: FormBody.Builder)
            : Response
    {
        val client = OkHttpClient()

        val site = "http://${ServerIP.serverIp}/${url}"

        var form = formBody.build()

        val request = Request.Builder().url(site).delete(form).build()

        val response = client.newCall(request).execute()

        return response
    }


    // Patch
    fun useThreadPatch(url:String, formBody: FormBody.Builder)
            : Response
    {
        val client = OkHttpClient()

        val site = "http://${ServerIP.serverIp}/${url}"

        var form = formBody.build()

        val request = Request.Builder().url(site).patch(form).build()

        val response = client.newCall(request).execute()

        return response
    }
}