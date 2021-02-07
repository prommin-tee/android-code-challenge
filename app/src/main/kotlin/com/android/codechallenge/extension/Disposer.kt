package com.android.codechallenge.extension

import io.reactivex.disposables.CompositeDisposable

interface Disposer {
  val disposeBag: CompositeDisposable
}
