package com.developer.smartfox.fragmentdemo.activity

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.developer.smartfox.fragmentdemo.App
import com.developer.smartfox.fragmentdemo.common.plusAssign
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class HomeActivityPresenter : MvpPresenter<HomeActivityView>() {


    private val depthInteractor = App.graph.depthInteractor

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        cd += depthInteractor.isDepthStateSubject.subscribe {
            viewState.animMenu(it)
        }

        cd += depthInteractor.addFragmentSubject.subscribe {
            viewState.addFragment(it.tab, it.vNumber, it.realNumber)
        }

        cd += depthInteractor.deleteFragmentSubject.subscribe {
            viewState.deleteFragment(it.tab, it.vNumber, it.realNumber)
        }

        cd += depthInteractor.popSubject.subscribe {
            viewState.popBackStackByTag(it.tab, it.vNumber, it.realNumber)
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
}