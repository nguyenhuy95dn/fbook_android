package com.framgia.fbook.screen.sharebook;

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.GoogleBook
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.screen.BasePresenter;
import com.framgia.fbook.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ShareBookContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {

    fun onInputTitleError(errorMsg: String?)

    fun onInputAuthorError(errorMsg: String?)

    fun onInputDescriptionError(errorMsg: String?)

    fun onGetOfficeSuccess(listOffice: List<Office>?)

    fun onGetCategorySuccess(listCategory: List<Category>?)

    fun onAddBookSuccess(book: Book?)

    fun onError(baseException: BaseException)

    fun onShowProgressDialog()

    fun onDismissProgressDialog()

    fun onSearchBookFromInternalSuccess(listBook: List<Book>?)

    fun onSearchBookFromGoogleBookSuccess(listGoogleBook: List<GoogleBook>?)
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun validateDataInput(bookRequest: BookRequest): Boolean

    fun getData()

    fun addBook(bookRequest: BookRequest)

    fun searchBookFromInternal(keyword: String?, field: String?)

    fun searchBookFromGoogleBook(title: String?)
  }
}
