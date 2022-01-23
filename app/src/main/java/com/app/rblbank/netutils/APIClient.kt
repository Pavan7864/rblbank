package com.app.rblbank.netutils

import android.app.ProgressDialog
import android.content.Context
import com.example.oncric.netutils.APIInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object APIClient {

    private var isTokenRefreshing = false





    fun response(
        call: Call<String>,
        currActivity: Context?,
        isProgress: Boolean,
        retrofitResponse: RetrofitResponse
    ) {
        var pd:ProgressDialog? = null
        if(currActivity!=null){
            pd = ProgressDialog(currActivity)
            pd.setMessage("Loading...")
            pd.setCancelable(false)
            if (isProgress) pd.show()
        }else
            null

        call.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                pd?.dismiss()
                try {
                    if (response.code() == 200)
                        return retrofitResponse.onResponse(response.body());
                } catch (e: java.lang.Exception) {
                    retrofitResponse.onResponse(null)
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                pd?.dismiss()
                retrofitResponse.onResponse(null)
            }
        })
    }


    fun getClient(): APIInterface {
        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor(object : Interceptor {
                @NotNull
                @Throws(IOException::class)
                override fun intercept(@NotNull chain: Interceptor.Chain): okhttp3.Response {
                    val originalRequest: Request = chain.request()
                    val builder: Request.Builder = originalRequest.newBuilder()
//                        .header("abc", "abc")
//                        .header("BASIC_AUTH_USER", "cric360")
//                        .header("BASIC_AUTH_PASSWORD", "5jnYxt=&mMZ2THuh4LxgVb%W+zXbw!8yrp^WHD")
//                        .header("Authorization", "Basic Y3JpYzM2MDo1am5ZeHQ9Jm1NWjJUSHVoNEx4Z1ZiJVcrelhidyE4eXJwXldIRA==")
//                        .header("accessToken", "")

                    val newRequest: Request = builder.build()
                    return chain.proceed(newRequest)
                }
            })
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

       val url =  "https://skcm.in/rbl_bank/api/"
        val retrofit= Retrofit.Builder() //  .baseUrl("http://websitedevelopment101.com/tabeby/public/")
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(APIInterface::class.java)
    }


}