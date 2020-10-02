
package com.poul.android.pikabu

import android.app.Application
import androidx.room.Room
import com.poul.android.pikabu.data.CeoPicabuRepository
import com.poul.android.pikabu.data.api.Api
import com.poul.android.pikabu.data.cache.PicabuDatabase
import com.poul.android.pikabu.data.cache.RoomCache
import com.poul.android.pikabu.domain.PicabuRepository
import com.poul.android.pikabu.presentation.fragment.postdetail.PicabuViewModelFactory
import com.poul.android.pikabu.presentation.fragment.post.PostListViewModelFactory
import com.poul.android.pikabu.presentation.fragment.favorites_post.FavoritesViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class App : Application() {

  override fun onCreate() {
    super.onCreate()

    val repository = createRepository()

    PostListViewModelFactory.inject(repository)
    PicabuViewModelFactory.inject(repository)
    FavoritesViewModelFactory.inject(repository)
  }

  private fun createRepository(): PicabuRepository {
    val api = Api.create()
    val database = createDatabase()
    val dao = database.picabuDao()
    val cache = RoomCache(dao)

    return CeoPicabuRepository(api, cache)
  }

  private fun createDatabase(): PicabuDatabase =
      Room.databaseBuilder(this, PicabuDatabase::class.java, "pikabu.db")
          .build()
}