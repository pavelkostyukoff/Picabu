
package com.poul.android.pikabu.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
      CachedPicabu::class
    ],
    version = 1
)
abstract class PicabuDatabase : RoomDatabase() {

  abstract fun picabuDao(): PostDao
}