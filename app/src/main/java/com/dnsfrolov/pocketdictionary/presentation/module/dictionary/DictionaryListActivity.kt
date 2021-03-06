package com.dnsfrolov.pocketdictionary.presentation.module.dictionary

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.dnsfrolov.pocketdictionary.R
import com.dnsfrolov.pocketdictionary.data.model.Word
import com.dnsfrolov.pocketdictionary.presentation.adapter.DictionaryListAdapter
import com.dnsfrolov.pocketdictionary.presentation.base.BaseMvpActivity
import com.dnsfrolov.pocketdictionary.presentation.module.dictionary.detail.DictionaryDetailActivity
import com.dnsfrolov.pocketdictionary.presentation.widget.ToolbarLayout
import com.dnsfrolov.pocketdictionary.util.OnWordClickListener
import kotlinx.android.synthetic.main.activity_dictionary_list.*

/**
 * project: PocketDictionary
 * date: 05.12.2017
 * owner: SegaSunset
 * email: denis.frolov.work@gmail.com
 */
class DictionaryListActivity :
		BaseMvpActivity<DictionaryListContract.View, DictionaryListContract.Presenter>(),
		DictionaryListContract.View, View.OnClickListener, OnWordClickListener {

	private lateinit var adapter: DictionaryListAdapter
	private var show: Boolean = false

	override fun getContentView(): Int {
		return R.layout.activity_dictionary_list
	}

	override fun configureToolbar(toolbar: ToolbarLayout) {
		toolbar.setEndFirstIcon(getDrawable(R.drawable.ic_settings))
		toolbar.setEndFirstIconClickListener(this)
	}

	override fun configureUI() {
		initAdapter()
		initFab()
		initScrollingListener()
	}

	override fun providePresenter(): DictionaryListContract.Presenter {
		return DictionaryListPresenterImpl()
	}

	override fun configurePresenter() {
		presenter.getWordList()
	}

	override fun showWordList(dictionaryList: List<Word>) {
		adapter.setNewData(dictionaryList)
	}

	override fun onWordClick(wordId: Int) {
		startActivity(DictionaryDetailActivity.newIntent(this, DictionaryDetailActivity.UPDATE, wordId))
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			ToolbarLayout.END_FIRST_ICON -> {
				show = if (!show) {
					toolbar.setEndSecondIcon(getDrawable(R.drawable.ic_prefs))
					toolbar.setEndThirdIcon(getDrawable(R.drawable.ic_search))
					toolbar.setEndSecondClickListener(this)
					toolbar.setEndThirdClickListener(this)
					toolbar.validateEndSecondIcon(true)
					toolbar.validateEndThirdIcon(true)
					true
				} else {
					toolbar.validateEndSecondIcon(false)
					toolbar.validateEndThirdIcon(false)
					toolbar.setEndSecondClickListener(null)
					toolbar.setEndThirdClickListener(null)
					false
				}
				if (toolbar.isSearchLayoutVisible()) {
					toolbar.setSearchLayoutVisibility(false)
				}
			}
			ToolbarLayout.END_SECOND_ICON -> {
				Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
			}
			ToolbarLayout.END_THIRD_ICON -> {
				toolbar.validateEndSecondIcon(false)
				toolbar.validateEndThirdIcon(false)
				toolbar.setEndSecondClickListener(null)
				toolbar.setEndThirdClickListener(null)
				toolbar.setSearchLayoutVisibility(true)
				show = false
			}
		}
	}

	private fun initAdapter() {
		adapter = DictionaryListAdapter(this)
		rv_dictionary_list.adapter = adapter
	}

	private fun initFab() {
		fab_add.setOnClickListener(this)
	}

	private fun initScrollingListener() {
		rv_dictionary_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
				if (dy > 0 && fab_add.visibility == View.VISIBLE) fab_add.hide()
				else if (dy < 0 && fab_add.visibility != View.VISIBLE) fab_add.show()
			}
		})
	}
}