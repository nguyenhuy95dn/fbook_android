package com.framgia.fbook.data.source.remote.api.service

import com.framgia.fbook.data.model.BookType
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.SearchUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface NameApi {
  @GET("/search/users")
  fun searchGithubUsers(@Query("per_page") limit: Int,
      @Query("q") searchTerm: String?): Single<SearchUserResponse>

  @GET("/users/{username}")
  fun getUser(@Path("username") username: String?): Single<User>

  @GET("/api/v0/home/")
  fun getHome(@Query("office_id") officeId: Int?): Single<BaseResponse<List<BookType>>>
}
