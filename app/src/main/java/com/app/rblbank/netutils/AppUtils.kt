package com.example.oncric.utils


import android.app.Activity
import android.app.Dialog
import android.graphics.Typeface
import android.text.Html
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.app.rblbank.R
import java.text.DecimalFormat


import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.*


object AppUtils {

    var serverUTCDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var serverChatUTCDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var whoWins = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    var serverDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val df = DecimalFormat("0.00")
    val dfI = DecimalFormat("######")
    init {




        serverUTCDateTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverChatUTCDateTimeFormat.timeZone = TimeZone.getTimeZone("UTC")

        serverDateTimeFormat.timeZone = TimeZone.getDefault()
    }



    fun String?.isEmpty(value:String?) = this?.isEmpty() ?: true


    fun String.isValidEmail() =
        isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun isValidMobile(value:String):Boolean{
            return value.length<10
        }

    fun hideProgress(overlayDialog: Dialog) {
        if (overlayDialog.isShowing) {
            overlayDialog.dismiss()
        }
    }

    fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.currentFocus != null && activity.currentFocus!!.windowToken != null) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            try {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            } catch (ignored: NullPointerException) {

            }
        }
    }

    fun convertDateFormat(dateTimeString: String?,
                          originalFormat: SimpleDateFormat, targetFormat: SimpleDateFormat): String {
        if (dateTimeString == null || dateTimeString.equals("null", ignoreCase = true))
            return ""

        var targetDateString = dateTimeString
        try {
            val originalDate = originalFormat.parse(dateTimeString)
            val originalDateString = originalFormat.format(originalDate)
            val targetDate = originalFormat.parse(originalDateString)
            targetDateString = targetFormat.format(targetDate)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return targetDateString!!
        }
    }

    fun convertNewsDateFormat(dateTimeString: String?, originalFormat: SimpleDateFormat, targetFormat: SimpleDateFormat): String {
        if (dateTimeString == null || dateTimeString.equals("null", ignoreCase = true))
            return ""

        var targetDateString = dateTimeString
        try {
            val originalDate = originalFormat.parse(dateTimeString)
            val originalDateString = originalFormat.format(originalDate)
            val targetDate = originalFormat.parse(originalDateString)
            targetDateString = targetFormat.format(targetDate)
            val diff: Long = Calendar.getInstance().time.time - targetDate.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            if(seconds<60 && minutes==0L){
                if(seconds==1L){
                    targetDateString="Sec. Ago"
                }else{
                    targetDateString="$seconds Secs Ago"
                }
            }else if(minutes<60 && hours==0L){
                if(minutes==1L){
                    targetDateString="1 Min Ago"
                }else{
                    targetDateString="$minutes Mins Ago"
                }
            }else if(hours<24 && days==0L){
                if(hours==1L){
                    targetDateString="1 Hr Ago"
                }else{
                    targetDateString="$hours Hrs Ago"
                }
            }else if(days<7){
                if(days==1L){
                    targetDateString="1 Day Ago"
                }else{
                    targetDateString="$days Days Ago"
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return targetDateString!!
        }
    }

    fun isValidFormat(dateTimeString: String?, format: SimpleDateFormat): Boolean {
        return try {
            format.parse(dateTimeString)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun convertNullToEmpty(vararg texts: String?): String {
        var text = ""
        texts
                .filterNotNull()
                .forEach { text += it }
        return text.trim { it <= ' ' }
    }

    const val IMAGE_TYPE_CITIES = "Cities"
    const val IMAGE_TYPE_CITIES_AIRPOT = "CityAirports"
    const val IMAGE_TYPE_CITIES_RILWAY = "CityRailways"
    const val IMAGE_TYPE_CITIES_BUSTAND = "CityBusTerminals"
    const val IMAGE_TYPE_USER_IMAGE = "UserProfileImg"
    const val IMAGE_TYPE_CITY_CATEGORY = "CitiesCategories"
    const val IMAGE_TYPE_ADS = "addresImages"

    const val AWS_IMAGE = "awsImage"


    fun setText(textView: TextView, text: String?) {
        if (text != null && !text.equals("null", ignoreCase = true)) {
            textView.text = text
        } else {
            textView.text = ""
        }
    }

    fun setText(textView: TextView, text: String?, defaultText: String) {
        if (text != null && !text.isEmpty() && !text.equals("null", ignoreCase = true)) {
            textView.text = text
        } else {
            textView.text = defaultText
        }
    }




    fun setText(textView: EditText, text: String?) {
        if (text != null && !text.equals("null", ignoreCase = true)) {
            textView.setText(text)
        } else {
            textView.setText("")
        }
    }


    fun setText(textView: EditText, text: Double?) {
        if (text != null) {
            textView.setText(text.toString())
        } else {
            textView.setText("")
        }
    }


    fun setTexts(textView: TextView, vararg texts: String) {
        var text = ""
        for (test in texts) {
            if (test != null && !test.equals("null", ignoreCase = true))
                text += test
            else
                text = ""
        }

        text = text.replace(", ,".toRegex(), ",")
        textView.text = Html.fromHtml(text.trim { it <= ' ' })
    }

    fun convertToString(value: Any): String {
        return if (value == 0 || value == 0L || value == 0.0 || value == 0f)
            ""
        else
            value.toString()
    }

    fun showToast(activity: Activity?, message: String, isError: Boolean) {
        if (activity != null) {
            val toast = Toast(activity)

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER.toFloat())
            val textView = TextView(activity)
            textView.setPadding(15, 15, 15, 15)
            textView.setTypeface(textView.typeface, Typeface.BOLD)
            textView.setTextColor(activity.resources.getColor(R.color.white))
            textView.text = message
            if (isError)
                textView.setBackgroundColor(activity.resources.getColor(R.color.colorRed))
            else
                textView.setBackgroundColor(activity.resources.getColor(R.color.colorAccent))
            textView.layoutParams = params

            toast.view = textView
            toast.duration = Toast.LENGTH_LONG
            toast.show()
        }
    }

    private var dialog: Dialog? = null




}
