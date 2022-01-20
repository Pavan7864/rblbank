package com.example.oncric.netutils

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap


interface APIInterface {


    @GET("logo.php")
    fun getPolls(): Call<String>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("email") email:String,
        @Field("mobile") mobile:String
    ): Call<String>

    @FormUrlEncoded
    @POST("contact.php")
    fun contact(
        @Field("first_name") first_name:String,
        @Field("last_name") last_name:String,
        @Field("mobile") mobile:String,
        @Field("address_1") address:String,
        @Field("address_2") address_2:String,
        @Field("city") city:String,
        @Field("state") state:String,
        @Field("pincode") pincode:String,
        @Field("card_no") card_no:String
    ): Call<String>

}
