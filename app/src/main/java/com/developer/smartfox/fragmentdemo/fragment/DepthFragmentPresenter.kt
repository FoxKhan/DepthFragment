package com.developer.smartfox.fragmentdemo.fragment

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.developer.smartfox.fragmentdemo.App
import com.developer.smartfox.fragmentdemo.common.plusAssign
import io.reactivex.disposables.CompositeDisposable


@InjectViewState
class DepthFragmentPresenter : MvpPresenter<DepthFragmentView>() {
    private val cd = CompositeDisposable()
    private var isDepthState = false

    private val depthInteractor = App.graph.depthInteractor
    var fragmentTab: String = ""
    var fragmentVisibleNumber: Int = -1
        set(value) {
            viewState.setFragmentNumber(value.toString())
            field = value
        }
    var fragmentRealNumber: Int = -1

    override fun onFirstViewAttach() {
        cd += depthInteractor.isDepthStateSubject.subscribe {
            if (it.first != isDepthState) {
                viewState.animDepth(it, fragmentRealNumber)
                isDepthState = !isDepthState
            }
        }

        cd += depthInteractor.deleteFragmentSubject.subscribe {
            if (it.first == fragmentTab && fragmentVisibleNumber > it.second)
                fragmentRealNumber--
        }
    }

    fun depthAnimComplete() {
        depthInteractor.depthAnimComplete()
    }

    override fun onDestroy() {
        cd.dispose()
        cd.clear()
    }

    fun addFragmentClick() {
        depthInteractor.addFragment(fragmentTab, fragmentVisibleNumber + 1)
    }
}
