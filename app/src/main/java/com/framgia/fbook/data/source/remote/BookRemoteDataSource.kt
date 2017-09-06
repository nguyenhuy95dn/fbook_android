package com.framgia.fbook.data.source.remote

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.BookDataSource
import com.framgia.fbook.data.source.remote.api.request.SearchBookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.service.NameApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Hyperion on 9/5/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class BookRemoteDataSource @Inject constructor(nameApi: NameApi) : BaseRemoteDataSource(
    nameApi), BookDataSource.BookRemoteDataSource {
  override fun searchBook(
      searchBookRequest: SearchBookRequest): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return nameApi.searchBook(searchBookRequest)
  }

}