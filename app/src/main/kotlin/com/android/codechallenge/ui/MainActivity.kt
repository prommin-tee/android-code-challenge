package com.android.codechallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.codechallenge.R

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      val fragment = MainFragment()
      supportFragmentManager.beginTransaction()
        .add(R.id.container, fragment, MAIN_FRAGMENT_TAG)
        .commit()
    }

  }

  companion object {
    const val MAIN_FRAGMENT_TAG = "MainFragment"
  }
}