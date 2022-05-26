package com.app.reddittask.data.remote

import com.app.reddittask.MyApp
import com.app.reddittask.utils.AppUtils.Companion.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

open class ConnectivityInterceptor : Interceptor {

    private val isConnected: Boolean
        get() {
            return isInternetAvailable(MyApp.getInstance())
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected) {
            throw NoNetworkException()
        }
        return chain.proceed(originalRequest)
    }

    class NoNetworkException internal constructor() : IOException("NO INTERNET CONNECTION")
}