package com.android.post.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import java.util.HashSet

class NetworkStateReceiver : BroadcastReceiver() {

    private val TAG = NetworkStateReceiver::class.java.name
    private var listeners: MutableSet<OnNetworkStateReceiverListener> = HashSet()
    private var connected: Boolean? = null


    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        connected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting
        notifyStateToAll()
    }

    private fun notifyStateToAll() {
        Log.d(TAG, "notifyStateToAll() called")
        for (listener in listeners) {
            if (connected != null) listener.networkAvailable()
            else listener.networkUnavailable()
        }
    }

    fun addListener(listener: OnNetworkStateReceiverListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: OnNetworkStateReceiverListener) {
        listeners.remove(listener)
    }

    interface OnNetworkStateReceiverListener {

        fun networkAvailable()

        fun networkUnavailable()
    }
}
