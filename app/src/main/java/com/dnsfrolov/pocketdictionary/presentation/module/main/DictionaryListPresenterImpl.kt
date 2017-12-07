package com.dnsfrolov.pocketdictionary.presentation.module.main

import com.dnsfrolov.gymtracker.app.base.mvp.BasePresenterImpl
import com.dnsfrolov.pocketdictionary.data.interactor.DictionaryInteractor
import com.dnsfrolov.pocketdictionary.data.interactor.DictionaryInteractorImpl

/**
 * project: PocketDictionary
 * date: 05.12.2017
 * owner: SegaSunset
 * email: denis.frolov.work@gmail.com
 */
class DictionaryListPresenterImpl :
		BasePresenterImpl<DictionaryListContract.View>(),
		DictionaryListContract.Presenter {

	private val exerciseInteractor: DictionaryInteractor = DictionaryInteractorImpl()

	override fun getWordList() {
		view?.showWordList(exerciseInteractor.getDictionaryList())
	}
}