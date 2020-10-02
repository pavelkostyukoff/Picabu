
package com.poul.android.pikabu.domain

import kotlinx.coroutines.flow.Flow

interface PicabuRepository {

  fun getCachedPost(): Flow<List<Post>>
  fun getCachedFavoritePicabu(): Flow<List<Post>>
  suspend fun getApiPicabu(): List<Post>
  suspend fun updateCachedPost(posts: List<Post>)
  suspend fun updatePostFavoriteStatus(postPicture: String, isFavorite: Boolean)
}