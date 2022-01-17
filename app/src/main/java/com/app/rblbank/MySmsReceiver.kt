package com.app.rblbank

import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle

import android.content.Intent
import android.telephony.SmsMessage


class MySmsReceiver : BroadcastReceiver() {

    companion object{
         val TAG = MySmsReceiver::class.java.simpleName
        val pdu_type = "pdus"
         var mListener: MessageListener? = null

    }

    override fun onReceive(context: Context?, intent: Intent) {
        val data = intent.extras
        val pdus = data!!["pdus"] as Array<Any>?
        for (i in pdus!!.indices) {
            val smsMessage: SmsMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
            val message = "Sender : " + smsMessage.getDisplayOriginatingAddress()
                .toString() + "Email From: " + smsMessage.getEmailFrom()
                .toString() + "Emal Body: " + smsMessage.getEmailBody()
                .toString() + "Display message body: " + smsMessage.getDisplayMessageBody()
                .toString() + "Time in millisecond: " + smsMessage.getTimestampMillis()
                .toString() + "Message: " + smsMessage.getMessageBody()
            mListener!!.messageReceived(message)
        }
    }

    fun bindListener(listener: MessageListener) {
        mListener = listener
    }
}