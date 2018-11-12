package com.developer.smartfox.fragmentdemo.domain

import com.developer.smartfox.fragmentdemo.model.FragmentInfo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DepthInteractor @Inject constructor() {

    private var isDepthState = false
    private var isAnim = false

    val isDepthStateSubject = BehaviorSubject.create<Boolean>()
    val deleteFragmentSubject = PublishSubject.create<FragmentInfo>()
    val addFragmentSubject = PublishSubject.create<FragmentInfo>()
    val popSubject = PublishSubject.create<FragmentInfo>()
    val focusFragmentTag = BehaviorSubject.create<Int>()


    fun toggleDepthState() {
//        if (isAnim) return //TODO fix bug (Completable merge array don't work)
        isAnim = true
        isDepthState = !isDepthState
        isDepthStateSubject.onNext(isDepthState)
    }

    fun depthAnimComplete() {
        isAnim = false
    }

    fun addFragment(tab: String, visibleNumber: Int, realNumber: Int) {
        addFragmentSubject.onNext(FragmentInfo(tab, visibleNumber, realNumber))
    }

    fun popBackStackFragment(tab: String, visibleNumber: Int, realNumber: Int) {
        popSubject.onNext(FragmentInfo(tab, visibleNumber, realNumber))
    }

    fun deleteFragment(tab: String, number: Int, realNumber: Int) {
        deleteFragmentSubject.onNext(FragmentInfo(tab, number, realNumber))
    }
}