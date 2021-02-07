package com.android.codechallenge.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.codechallenge.service.JsonPlaceholderApi

class MainViewModelFactory constructor(
  private val context: Context,
  private val api: JsonPlaceholderApi
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
      MainViewModel(this.context, this.api) as T
    } else {
      throw IllegalArgumentException("ViewModel Not Found")
    }
  }
}