
package com.poul.android.pikabu.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

  @GET("feed.php")
  suspend fun getPostImages(): List<PicabuResponse>

    @GET("{id}")
    suspend fun getPostToId(@Path("id") id: Int): PicabuResponse

  companion object {
    private const val TAG = "Api"

    fun create(): Api {
      val retrofit = Retrofit.Builder()
          .baseUrl(ApiConstants.API)
          .client(okHttpClient)
          .addConverterFactory(MoshiConverterFactory.create())
          .build()

      return retrofit.create(Api::class.java)
    }

    private val okHttpClient: OkHttpClient
      get() = OkHttpClient.Builder()
          .addInterceptor(httpLoggingInterceptor)
          .build()

    private val httpLoggingInterceptor: HttpLoggingInterceptor
      get() {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
          override fun log(message: String) {
            Log.i(TAG, message)
          }
        })

        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        return interceptor
      }
  }
}