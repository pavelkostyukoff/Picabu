
package com.poul.android.pikabu.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

  @Query("SELECT * FROM Picabu")
  fun getAllPost(): Flow<List<CachedPicabu>>

  @Query("SELECT * FROM Picabu WHERE isFavourite = 1")
  fun getFavoritePost(): Flow<List<CachedPicabu>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(picabus: List<CachedPicabu>)

  @Query("UPDATE Picabu SET isFavourite = :isFavorite WHERE picture = :postPicture")
  fun update(postPicture: String, isFavorite: Boolean)
}