package com.framgia.fbook.screen.addCategoryFavorite;

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.AddCategoryFavoriteRequest
import com.framgia.fbook.databinding.ActivityAddCategoryFavoriteBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * AddCategoryFavorite Screen.
 */
class AddCategoryFavoriteActivity : BaseActivity(), AddCategoryFavoriteContract.ViewModel, ItemClickSelectCategoryListener {

  @Inject
  internal lateinit var mPresenter: AddCategoryFavoriteContract.Presenter
  @Inject
  internal lateinit var mAdapter: AddCategoryFavoriteAdapter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mCategoryRepository: CategoryRepository
  @Inject
  internal lateinit var mDialogManager: DialogManager
  private var tags: String = String()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerAddCategoryFavoriteComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .addCategoryFavoriteModule(AddCategoryFavoriteModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivityAddCategoryFavoriteBinding>(this,
        R.layout.activity_add_category_favorite)
    binding.viewModel = this
    mPresenter.getCategory()
    mAdapter.setItemListener(this)
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onError(exception: BaseException) {
    Log.e(TAG, exception.getMessageError())
  }

  override fun onGetCategorySuccess(category: List<Category>?) {
    category?.let { mAdapter.updateData(category) }
  }

  override fun onItemCategoryClick(category: Category?) {
    category?.favorite = category?.favorite?.not()
    mAdapter.updateOneItem(category)
  }

  override fun onUpdateCategoryFavoriteSuccess() {
    Toast.makeText(applicationContext, getString(R.string.update_success),
        Toast.LENGTH_SHORT).show()
    val user = mUserRepository.getUserLocal()
    user?.tag = tags
    mUserRepository.saveUser(user)
    mPresenter.getCategory()
  }

  fun onClickUpdate() {
    val addCategory: AddCategoryFavoriteRequest = AddCategoryFavoriteRequest()
    val addItemTag: AddCategoryFavoriteRequest.Tags = AddCategoryFavoriteRequest.Tags()
    tags = Constant.EXTRA_EMTY
    mAdapter.mListCategory
        .filter { it.favorite == true }
        .forEach { tags += it.id.toString() + Constant.EXTRA_COMMA }
    tags = tags.substring(0, tags.length - 1)
    tags.let {
      addItemTag.tags = tags
      addItemTag.let { addCategory.item = addItemTag }
    }
    mPresenter.updateCategory(addCategory)
  }

  companion object {
    val TAG: String = AddCategoryFavoriteActivity::class.java.name

    fun newInstance(): AddCategoryFavoriteActivity {
      return AddCategoryFavoriteActivity()
    }
  }
}
