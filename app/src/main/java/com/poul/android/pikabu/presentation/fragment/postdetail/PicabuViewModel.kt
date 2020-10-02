
package com.poul.android.pikabu.presentation.fragment.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poul.android.pikabu.domain.PicabuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PicabuViewModel(private val repository: PicabuRepository) : ViewModel() {

  fun updatePostFavoriteStatus(postPicture: String, isFavorite: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.updatePostFavoriteStatus(postPicture, isFavorite)
    }
  }
}