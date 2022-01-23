package com.app.rblbank.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import com.app.rblbank.R
import com.app.rblbank.netutils.APIClient
import com.app.rblbank.netutils.RetrofitResponse
import com.example.oncric.utils.AppUtils.isValidEmail
import kotlinx.android.synthetic.main.activity_login.*
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.app.rblbank.SmsBroadcastReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import org.json.JSONObject


class LoginActivity : LocalizeActivity() {


    companion object{

        fun open(activity:Activity){
            val intent = Intent(activity,LoginActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    lateinit var currActivity:Activity
    val TAG ="pppp"

    private val PERMISSIONS_RECEIVE_SMS = 200
    private val PERMISSIONS_REQUEST_READ_SMS = 200
    private val REQ_USER_CONSENT = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        currActivity = this
        btnLogin.setOnClickListener {
            getLogo();
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    PERMISSIONS_RECEIVE_SMS)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_RECEIVE_SMS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG, "PERMISSIONS_RECEIVE_SMS permission granted")

                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED) {

                        // Permission is not granted
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.READ_SMS)) {
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(this,
                                arrayOf(Manifest.permission.READ_SMS),
                                PERMISSIONS_REQUEST_READ_SMS)

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        // Permission has already been granted
                    }


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "PERMISSIONS_RECEIVE_SMS permission denied")
                }
                return
            }

            PERMISSIONS_REQUEST_READ_SMS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG, "PERMISSIONS_REQUEST_READ_SMS permission granted")
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "PERMISSIONS_REQUEST_READ_SMS permission denied")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

            }
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            val REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(this, arrayOf( "android.permission.READ_SMS"), REQUEST_CODE_ASK_PERMISSIONS);
        }
    }





    /*{"status":1,"message":"please wait while we are verifying your email id with service provider"}*/
}