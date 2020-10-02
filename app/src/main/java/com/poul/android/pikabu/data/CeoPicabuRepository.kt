
package com.poul.android.pikabu.data

import com.poul.android.pikabu.data.api.Api
import com.poul.android.pikabu.data.cache.Cache
import com.poul.android.pikabu.data.cache.CachedPicabu
import com.poul.android.pikabu.domain.Post
import com.poul.android.pikabu.domain.PicabuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CeoPicabuRepository(private val api: Api, private val cache: Cache) : PicabuRepository {

  override fun getCachedPost(): Flow<List<Post>> {
    return cache.getAllPosts()
        .map { it ->
          it.map { it.toDomainEntity() }
        }
  }

  override fun getCachedFavoritePicabu(): Flow<List<Post>> {
    return cache.getFavoritePosts()
        .map { favoritePost ->
          favoritePost.map { it.toDomainEntity() }
        }
  }

  override suspend fun getApiPicabu(): List<Post> {
    return api.getPostImages().map {
        var image = "https://cs9.pikabu.ru/post_img/2020/06/23/8/broken_image.jpg"
        var body = ""
        if (it.images != null) {
            it.images.let {
                it.map {
                    it.let {
                        image = it!!
                    }
                }
            }
        }
        if (it.body != null) {
            it.body.let {
                body  = it
            }
        }
                    Post(id = it.id,title = it.title, picture = image, body = body, isFavourite = false)
    }
  }

  override suspend fun updateCachedPost(posts: List<Post>) {
    cache.updateCachedPosts(posts.map { it.picture?.let { picture -> it.id?.let { id -> it.title?.let { title -> it.body?.let { it4 -> CachedPicabu(id, title, picture, it4,it.isFavourite) } } } } } as List<CachedPicabu>)
  }

  override suspend fun updatePostFavoriteStatus(postPicture: String, isFavorite: Boolean) {
    cache.updatePostsFavoriteStatus(postPicture, isFavorite)
  }
}