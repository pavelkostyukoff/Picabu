
package com.poul.android.pikabu.data.api

import com.squareup.moshi.Json

data class PicabuResponse(
  @field:Json(name = "id") val id: Int,
  @field:Json(name = "title") val title: String,
  @field:Json(name = "images") val images: List<String?> = emptyList(),
  @field:Json(name = "body") val body: String?
)