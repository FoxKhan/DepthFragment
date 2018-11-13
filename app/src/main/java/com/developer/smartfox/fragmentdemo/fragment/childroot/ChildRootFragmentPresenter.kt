package com.developer.smartfox.fragmentdemo.fragment.childroot

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.developer.smartfox.fragmentdemo.App
import com.developer.smartfox.fragmentdemo.common.plusAssign
import com.developer.smartfox.fragmentdemo.navigator.Navigator
import io.reactivex.disposables.CompositeDisposable


@InjectViewState
class ChildRootFragmentPresenter(private val navigator: Navigator, private val tab: String) :
    MvpPresenter<ChildRootFragmentView>() {


    private val depthInteractor = App.graph.depthInteractor

    private val cd = CompositeDisposable()

    init {

        cd += depthInteractor.addFragmentSubject.subscribe {
            if (it.tab == tab)
                navigator.addFragment(it.vNumber, it.realNumber)
        }

        cd += depthInteractor.deleteFragmentSubject.subscribe {
            if (it.tab == tab)
                navigator.deleteFragment(it.vNumber, it.realNumber)
        }

        cd += depthInteractor.popSubject.subscribe {
            if (it.tab == tab)
                navigator.popBackStackByTag(it.vNumber)
//        }
        }
    }
}