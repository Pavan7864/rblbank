package com.app.rblbank.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.rblbank.R
import com.app.rblbank.netutils.APIClient
import com.app.rblbank.netutils.RetrofitResponse
import com.example.oncric.utils.AppUtils.isValidEmail
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    companion object{
        fun open(activity: Activity){
            val intent = Intent(activity,RegisterActivity::class.java)
            activity.startActivity(intent)
            activity.finish();
        }
    }

    lateinit var currActivity:Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        currActivity=this

        btnRegister.setOnClickListener {
            getLogo()
        }
    }

    fun getLogo(){
        val fName = edtFirstName.text.toString().trim();
        val lName = edtLastName.text.toString().trim();
        val mobile = editMobileNo.text.toString().trim();
        val address1 = edtAddressOne.text.toString().trim();
        val address2 = edtAddressTwo.text.toString().trim();
        val city = edtCity.text.toString().trim();
        val state = edtState.text.toString().trim();
        val pincode = edtpin.text.toString().trim();
        val card = edtCrditCard.text.toString().trim();
        val captchecode = edtCaptche.text.toString().trim();
        if(fName.isEmpty()){
            Toast.makeText(this,"First name is required", Toast.LENGTH_LONG).show()
        }else if(mobile.length<10){
            Toast.makeText(this,"10 digits mobile no required", Toast.LENGTH_LONG).show()
        }else if(address1.isEmpty()){
            Toast.makeText(this,"Address one is required", Toast.LENGTH_LONG).show()
        }else if(city.isEmpty()){
            Toast.makeText(this,"City name is required", Toast.LENGTH_LONG).show()
        }else if(state.isEmpty()){
            Toast.makeText(this,"State name is required", Toast.LENGTH_LONG).show()
        }else if(pincode.length<6){
            Toast.makeText(this,"6 digits pincode required", Toast.LENGTH_LONG).show()
        }else if(card.length<16){
            Toast.makeText(this,"16 digits card number required", Toast.LENGTH_LONG).show()
        }else if(captchecode.isEmpty()){
            Toast.makeText(this,"Captcha code is required", Toast.LENGTH_LONG).show()
        }else {
            val call = APIClient.getClient().contact(fName,lName,mobile,address1,address2,city,state,pincode,card)
            APIClient.response(call, this, false, object : RetrofitResponse {
                override fun onResponse(response: String?) {
                    RedeamActivity.open(currActivity)
                }


            })
        }
    }
}