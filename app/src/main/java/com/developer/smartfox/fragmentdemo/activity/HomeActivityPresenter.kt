package com.developer.smartfox.fragmentdemo.activity

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.developer.smartfox.fragmentdemo.App
import com.developer.smartfox.fragmentdemo.common.plusAssign
import com.developer.smartfox.fragmentdemo.navigator.Navigator
import com.developer.smartfox.fragmentdemo.navigator.NavigatorHolder
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class HomeActivityPresenter : MvpPresenter<HomeActivityView>() {

    init {
        viewState.replaceFragment(NavigatorHolder.SQUARE)
    }


    private val depthInteractor = App.graph.depthInteractor

    private val cd = CompositeDisposable()

//    private var navigator : Navigator? = null

    override fun onFirstViewAttach() {
        cd += depthInteractor.isDepthStateSubject.subscribe {
            viewState.animMenu(it)
        }
    }

    fun onMenuClick() {
        depthInteractor.toggleDepthState()
    }

    fun onStop() {
        depthInteractor.depthAnimComplete()
    }


    override fun onDestroy() {
        cd.dispose()
        cd.clear()
    }

    fun onFragmentDelete(tab: String, number: Int, realNumber: Int) {
        depthInteractor.deleteFragment(tab, number, realNumber)
    }

    fun onChangeFocusFragment(focusFragmentTag: Int) {
        depthInteractor.focusFragmentTag.onNext(focusFragmentTag)
    }

    fun btmTabClick(tab: String) {

        viewState
    }
}