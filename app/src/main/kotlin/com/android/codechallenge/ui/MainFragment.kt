package com.android.codechallenge.ui

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.codechallenge.R
import com.android.codechallenge.adapter.PhotoListAdapter
import com.android.codechallenge.base.BaseFragment
import com.android.codechallenge.data.PhotoResponse
import com.android.codechallenge.databinding.MainFragmentBinding
import com.android.codechallenge.service.ApiFactory
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

class MainFragment : BaseFragment() {

  private lateinit var binding: MainFragmentBinding
  private lateinit var viewModel: MainViewModel

  private lateinit var textToSpeech: TextToSpeech

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel = ViewModelProvider(
      this,
      MainViewModelFactory(
        requireContext(), ApiFactory(requireContext()).jsonPlaceholder
      )
    ).get(MainViewModel::class.java)

    setUpTextToSpeech()

    binding.vm = viewModel

    viewModel.loadPhotos()

    viewModel.photoResponseData.observe(viewLifecycleOwner, Observer { dataList ->
      val adapter = PhotoListAdapter(dataList,this::onPhotoItemClick)
      photosRecyclerView.adapter = adapter
    })
  }

  private fun setUpTextToSpeech() {
    textToSpeech = TextToSpeech(requireContext(), TextToSpeech.OnInitListener {
      if (it != TextToSpeech.ERROR) {
        textToSpeech.language = Locale.US
      }
    })
  }

  private fun onPhotoItemClick(data: PhotoResponse) {
    if (data.title.isNotEmpty()) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        textToSpeech.speak(data.title,TextToSpeech.QUEUE_FLUSH,null,null)
      }else{
        textToSpeech.speak(data.title,TextToSpeech.QUEUE_FLUSH,null)
      }
    }
  }

  override fun onPause() {
    if (textToSpeech.isSpeaking) {
      textToSpeech.stop()
    }
    super.onPause()
  }

  companion object {
    fun newInstance() = MainFragment()
  }

}