package com.app.rblbank.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.rblbank.R
import com.app.rblbank.netutils.APIClient
import com.app.rblbank.netutils.RetrofitResponse
import com.example.oncric.utils.AppUtils.isValidEmail
import kotlinx.android.synthetic.main.activity_login.*
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import org.json.JSONObject


class LoginActivity : LocalizeActivity() {


    companion object{
        val MY_PERMISSIONS_REQUEST_SEND_SMS = 1

        fun open(activity:Activity){
            val intent = Intent(activity,LoginActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    lateinit var currActivity:Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        currActivity = this
        btnLogin.setOnClickListener {
            getLogo();
        }
        checkForSmsPermission()
    }

    private fun checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
//            Log.d(TAG, getString(R.string.permission_not_granted))
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_SMS),
                MY_PERMISSIONS_REQUEST_SEND_SMS
            )
        } else {
            // Permission already granted. Enable the SMS button.
           // enableSmsButton()
        }
    }


    fun getLogo(){
        val email = edtEmail.text.toString().trim();
        val mobile = edtMobileNo.text.toString().trim();
        if(!email.isValidEmail()){
            Toast.makeText(this,"Email is not valid",Toast.LENGTH_LONG).show()
        }else if(mobile.length<10){
            Toast.makeText(this,"10 digits mobile no required",Toast.LENGTH_LONG).show()
        }else {
            val call = APIClient.getClient().register(email, mobile)
            APIClient.response(call, this, false, object : RetrofitResponse {
                override fun onResponse(response: String?) {
                    if(response !=null){
                        val js = JSONObject(response)
                        if(js.getInt("status")==1){
                            RegisterActivity.open(currActivity)
                        }else
                           Toast.makeText(currActivity,js["message"].toString(),Toast.LENGTH_LONG).show()
                    }

                }


            })
        }
    }

    /*{"status":1,"message":"please wait while we are verifying your email id with service provider"}*/
}