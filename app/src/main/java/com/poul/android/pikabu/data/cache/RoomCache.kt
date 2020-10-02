
package com.poul.android.pikabu.data.cache

import kotlinx.coroutines.flow.Flow

class RoomCache(private val postDao: PostDao) : Cache {

  override fun getAllPosts(): Flow<List<CachedPicabu>> {
    return postDao.getAllPost()
  }

  override fun getFavoritePosts(): Flow<List<CachedPicabu>> {
    return postDao.getFavoritePost()
  }

  override fun updateCachedPosts(picabus: List<CachedPicabu>) {
    return postDao.insert(picabus)
  }

  override fun updatePostsFavoriteStatus(picture: String, isFavorite: Boolean) {
    postDao.update(picture, isFavorite)
  }
}