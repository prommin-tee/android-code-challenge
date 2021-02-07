package com.android.codechallenge.service

import android.content.Context

class ApiFactory(
  private val context: Context
) {

  private val baseUrl = "https://jsonplaceholder.typicode.com"

  val jsonPlaceholder: JsonPlaceholderApi = RetrofitClient(context).retrofit(baseUrl).create(JsonPlaceholderApi::class.java)
}