package com.android.codechallenge.service

import com.android.codechallenge.data.PhotoResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface JsonPlaceholderApi {

  @GET("/photos")
  fun fetchPhotos(): Observable<List<PhotoResponse>>
}