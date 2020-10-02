
package com.poul.android.pikabu.data.cache

import kotlinx.coroutines.flow.Flow

interface Cache {

  fun getAllPosts(): Flow<List<CachedPicabu>>
  fun getFavoritePosts(): Flow<List<CachedPicabu>>
  fun updateCachedPosts(picabus: List<CachedPicabu>)
  fun updatePostsFavoriteStatus(postPicture: String, isFavorite: Boolean)
}