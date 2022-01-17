package com.app.rblbank

interface MessageListener {
    fun messageReceived(message: String?)
}