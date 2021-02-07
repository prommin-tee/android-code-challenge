package com.android.codechallenge.base

import androidx.lifecycle.ViewModel
import com.android.codechallenge.extension.Disposer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel(), Disposer {

  override val disposeBag: CompositeDisposable = CompositeDisposable()

  internal fun addDisposableInternal(d: Disposable) {
    this.disposeBag.add(d)
  }

  override fun onCleared() {
    super.onCleared()
    this.disposeBag.clear()
  }

}