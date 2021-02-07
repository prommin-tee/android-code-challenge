package com.android.codechallenge.service

import android.content.Context
import com.android.codechallenge.base.AppConstants
import com.android.codechallenge.network.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(
  context: Context
) {

  private val context = context

  /**
   * Initialize OkhttpClient with our interceptor
   */
  private fun okHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(AppConstants.timeOut, TimeUnit.SECONDS)
      .addInterceptor(NetworkConnectionInterceptor(context))
      .build()
  }

  fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient(context))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
}