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
    var fragmentTagNumber: Int = -1
        set(value) {
            viewState.setFragmentNumber(value.toString())
            field = value
        }
    var fragmentRealNumber: Int = -1

    override fun onFirstViewAttach() {
        cd += depthInteractor.isDepthStateSubject.subscribe {
            if (it != isDepthState) {
                isDepthState = !isDepthState
                viewState.animDepth(it, fragmentRealNumber)
            }
        }

        cd += depthInteractor.deleteFragmentSubject.subscribe {
            if (it.tab == fragmentTab && fragmentTagNumber > it.vNumber) {
                fragmentRealNumber--
                viewState.animDepth(isDepthState, fragmentRealNumber)
            }
        }

        cd += depthInteractor.focusFragmentTag.subscribe {
            if (it == fragmentRealNumber) viewState.setAddBtn(true)
            else viewState.setAddBtn(false)
        }

//        cd += depthInteractor.popSubject.subscribe {
//
//        }

//        cd += depthInteractor.addFragmentSubject.subscribe {
//            if (it.tab == fragmentTab && it.vNumber == fragmentTagNumber){
//                viewState.setAddBtn(true)
//            }else viewState.setAddBtn(false)
//        }
    }


    fun depthAnimComplete() {
        depthInteractor.depthAnimComplete()
    }

    override fun onDestroy() {
        cd.dispose()
        cd.clear()
    }

    fun addFragmentClick(fCont: Int) {
        depthInteractor.addFragment(fragmentTab, fragmentTagNumber + 1, fCont + 1)
    }

    fun deleteFragmentClick() {
        depthInteractor.deleteFragment(fragmentTab, fragmentTagNumber, fragmentRealNumber)
    }

    fun popFragmentClick() {
        depthInteractor.popBackStackFragment(fragmentTab, fragmentTagNumber, fragmentRealNumber)
    }
}
