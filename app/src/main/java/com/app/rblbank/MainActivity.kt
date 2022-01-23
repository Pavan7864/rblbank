package com.app.rblbank

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import com.app.rblbank.activities.LocalizeActivity
import com.app.rblbank.activities.LoginActivity
import com.app.rblbank.netutils.APIClient
import com.app.rblbank.netutils.RetrofitResponse

class MainActivity : LocalizeActivity() {
    val PREFERENCE_NAME="rblshared"
    lateinit var preference: SharedPreferences
    lateinit var currActivity:Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currActivity = this
        preference=getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        getLogo()
    }


    fun getLogo(){
        val call = APIClient.getClient().getPolls()
        APIClient.response(call,this,false,object : RetrofitResponse {
            override fun onResponse(response: String?) {
                val editor=preference.edit()
                editor.putString("logo",response)
                editor.commit()

                openLogin()
            }


        })
    }


    fun openLogin(){
        Handler().postDelayed({
            LoginActivity.open(currActivity)
        }, 1000L)

    }

    /*
    * {"status":1,"message":"Data Found","logo":"http:\/\/farookidoors.ga\/rbl_bank\/uploads\/uploads\/01.jpg"}*/

}