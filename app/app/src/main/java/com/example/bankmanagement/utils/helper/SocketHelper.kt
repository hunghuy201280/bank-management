package com.example.bankmanagement.utils.helper

import com.example.bankmanagement.constants.AppConfigs
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketHelper {
   private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket(AppConfigs.baseUrl)
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}