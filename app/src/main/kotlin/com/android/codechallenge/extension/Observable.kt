package com.android.codechallenge.extension

import com.android.codechallenge.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.subscribeWithViewModel(
  vm: BaseViewModel,
  onNext: (T) -> Unit,
  onError: (Throwable) -> Unit
): Disposable = this
  .subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
  .subscribe(onNext, {
    onError(it)
  })
