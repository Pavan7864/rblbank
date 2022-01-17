package com.app.rblbank.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.rblbank.R

class ThanksActivity : AppCompatActivity() {
    companion object{

        fun open(activity:Activity){
            val intent = Intent(activity,ThanksActivity::class.java)
            activity.startActivity(intent)
            activity.finish();
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanks)
    }

    override fun onBackPressed() {

    }
}