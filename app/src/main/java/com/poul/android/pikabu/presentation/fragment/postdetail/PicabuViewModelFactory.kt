
package com.poul.android.pikabu.presentation.fragment.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poul.android.pikabu.domain.PicabuRepository

@Suppress("UNCHECKED_CAST")
object PicabuViewModelFactory : ViewModelProvider.Factory {

  private lateinit var repository: PicabuRepository

  fun inject(repository: PicabuRepository) {
    PicabuViewModelFactory.repository = repository
  }

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return PicabuViewModel(repository) as T
  }
}