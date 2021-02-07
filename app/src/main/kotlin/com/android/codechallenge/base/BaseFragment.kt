package com.android.codechallenge.base

import androidx.fragment.app.Fragment
import com.android.codechallenge.extension.Disposer
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseFragment: Fragment(), CoroutineScope, Disposer {

  override val disposeBag by lazy { CompositeDisposable() }

  private val job = Job()

  override val coroutineContext = job + Dispatchers.Main

  override fun onDestroy() {
    super.onDestroy()
    job.cancel()
    disposeBag.dispose()
  }

}