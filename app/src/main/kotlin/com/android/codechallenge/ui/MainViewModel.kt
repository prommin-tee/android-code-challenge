package com.android.codechallenge.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.codechallenge.base.BaseViewModel
import com.android.codechallenge.data.PhotoResponse
import com.android.codechallenge.extension.disposedBy
import com.android.codechallenge.extension.subscribeWithViewModel
import com.android.codechallenge.service.JsonPlaceholderApi

class MainViewModel(
  private val context: Context,
  private val api: JsonPlaceholderApi
) : BaseViewModel() {

  val photoResponseData = MutableLiveData<List<PhotoResponse>>()
  val isLoadDataLoading = MutableLiveData<Boolean>()
  val isLoadDataError = MutableLiveData<Boolean>()
  val errorMessage = MutableLiveData<String>()

  fun loadPhotos() {
    isLoadDataLoading.value = true
    isLoadDataError.value = false
    api.fetchPhotos().subscribeWithViewModel(
      this,
      this::onLoadPhotosSuccess,
      this::onLoadPhotosFail
    ).disposedBy(this)
  }

  private fun onLoadPhotosSuccess(response: List<PhotoResponse>) {
    isLoadDataLoading.value = false
    photoResponseData.value = response
  }

  private fun onLoadPhotosFail(throwable: Throwable) {
    isLoadDataLoading.value = false
    isLoadDataError.value = true
    errorMessage.value = "Error message: ${throwable.message}"
  }

}