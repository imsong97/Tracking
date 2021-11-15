package com.yunho.tracking

import android.net.ConnectivityManager

class Utils {
    private val view = MainActivity()

    fun getNetworkState(): String {
        val ctx = view.getContext()

        val service = ctx.getSystemService(ConnectivityManager::class.java)
        val info = service.activeNetwork

        println(info)

        return ""
    }
}