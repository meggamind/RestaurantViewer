package com.aniket91.afiirm.restuarantviewer.extensions

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

fun <T> Call<T>.onEnqueue(
    actOnSuccess: (Response<T>) -> Unit,
    actOnFailure: (t: Throwable?) -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>?, t: Throwable?) {
            println("failed")
            actOnFailure(t)
        }

        override fun onResponse(call: Call<T>?, response: Response<T>) {
            println("onResponse")
            if (response.isSuccessful) {
                actOnSuccess(response)
            } else {
                onFailure(call, Exception(response.code().toString()))
            }
        }
    })
}