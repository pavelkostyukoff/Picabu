
package com.poul.android.pikabu.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.poul.android.pikabu.domain.Post

@Entity(tableName = "Picabu")
data class CachedPicabu(
    @PrimaryKey
    @ColumnInfo(index = true)
    val id: Int,
    val title: String,
    val picture: String,
    val body: String,
    val isFavourite: Boolean = false
) {

  fun toDomainEntity(): Post = Post(id,title, picture, body, isFavourite)
}
