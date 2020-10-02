
package com.poul.android.pikabu.presentation.fragment.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poul.android.pikabu.domain.Post
import com.poul.android.pikabu.domain.PicabuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PostListViewModel(
    private val repository: PicabuRepository
) : ViewModel() {

  companion object {
    const val PAGE_SIZE = 10
  }

  val postList: LiveData<List<Post>>
    get() = _postList

  private val _postList: MutableLiveData<List<Post>> = MutableLiveData()

  init {
    observeCacheUpdates()
  }

  fun getMorePost() {
    updateCacheWithPostFromApi()
  }

  private fun updateCacheWithPostFromApi() {
    viewModelScope.launch(Dispatchers.IO) {
      val posts = repository.getApiPicabu()
      repository.updateCachedPost(posts)
    }
  }

  private fun observeCacheUpdates() {
    viewModelScope.launch {
      repository.getCachedPost()
          .onEach {
             if (it.isEmpty()) {
               getMorePost()
            }
          }
          .flowOn(Dispatchers.IO)
          .collect { handle(it) }
    }
  }

  private fun handle(posts: List<Post>) {
    _postList.value = posts
  }
}