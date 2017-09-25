package com.framgia.fbook.screen.userinbookdetail.screen.UserReview

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.databinding.FragmentUserReviewBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.userinbookdetail.UserInBookDetailActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * UserReview Screen.
 */
class UserReviewFragment : BaseFragment(), UserReviewContract.ViewModel {

  companion object {

    private val TAB_USER_REVIEW: String = "TAB_USER_REVIEW"

    fun newInstance(book: Book): UserReviewFragment {
      val fragment = UserReviewFragment()
      val bundle = Bundle()
      bundle.putParcelable(UserReviewFragment.TAB_USER_REVIEW, book)
      fragment.arguments = bundle
      return fragment
    }
  }

  @Inject
  internal lateinit var mPresenter: UserReviewContract.Presenter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mDialogManager: DialogManager

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    val book: Book = activity.intent.getParcelableExtra(Constant.USER_BOOK_DETAIL_EXTRA)

    DaggerUserReviewComponent.builder()
        .userInBookDetailComponent(
            (activity as UserInBookDetailActivity).getUserInBookDetailComponent())
        .userReviewModule(UserReviewModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentUserReviewBinding>(inflater,
        R.layout.fragment_user_review, container,
        false)
    binding.viewModel = this
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }
}