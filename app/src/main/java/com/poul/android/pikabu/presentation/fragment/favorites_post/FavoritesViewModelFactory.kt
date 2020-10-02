

package com.poul.android.pikabu.presentation.fragment.favorites_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poul.android.pikabu.domain.PicabuRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Suppress("UNCHECKED_CAST")
object FavoritesViewModelFactory : ViewModelProvider.Factory {

  private lateinit var repository: PicabuRepository

  fun inject(repository: PicabuRepository) {
    FavoritesViewModelFactory.repository = repository
  }

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return FavoritesViewModel(repository) as T
  }
}