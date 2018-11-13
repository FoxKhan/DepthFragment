package com.developer.smartfox.fragmentdemo.fragment.childroot

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.developer.smartfox.fragmentdemo.App
import com.developer.smartfox.fragmentdemo.common.plusAssign
import com.developer.smartfox.fragmentdemo.navigator.Navigator
import com.developer.smartfox.fragmentdemo.navigator.NavigatorHolder.Companion.CIRCLE
import com.developer.smartfox.fragmentdemo.navigator.NavigatorHolder.Companion.SQUARE
import com.developer.smartfox.fragmentdemo.navigator.NavigatorHolder.Companion.TRIANGLE
import io.reactivex.disposables.CompositeDisposable


@InjectViewState
class ChildRootFragmentPresenter(private val navigator: Navigator, private val tab: String) :
    MvpPresenter<ChildRootFragmentView>() {

    private val depthInteractor = App.graph.depthInteractor

    private val cd = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        cd.dispose()
        cd.clear()
    }

    override fun onFirstViewAttach() {

        cd += depthInteractor.addFragmentSubject
            .filter { it.tab.contains(tab) }.subscribe {
                addFragment(it.vNumber, it.realNumber)
            }

        cd += depthInteractor.deleteFragmentSubject
            .filter { it.tab.contains(tab) }.subscribe {
                navigator.deleteFragment(it.vNumber)
            }

        cd += depthInteractor.popSubject
            .filter { it.tab.contains(tab) }.subscribe {
                navigator.popBackStackByTag(it.vNumber)
            }

        cd += depthInteractor.backPressedSubject
            .filter { tab == it }.subscribe{
            if (navigator.popBackStack()) depthInteractor.finishSubject.onNext(Unit)
        }

        addFragment(1, 1)
    }

    private fun addFragment(vNumber: Int, rNumber: Int) {
        when (tab) {
            SQUARE -> navigator.addFragment(vNumber, rNumber)
            CIRCLE -> navigator.replaceFragmentWithBackStack(vNumber, rNumber)
            TRIANGLE -> navigator.replaceFragment(vNumber, rNumber)
        }
    }
}