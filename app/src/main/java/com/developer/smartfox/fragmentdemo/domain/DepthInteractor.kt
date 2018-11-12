package com.developer.smartfox.fragmentdemo.domain

import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DepthInteractor @Inject constructor() {

    private var isDepthState = false
    private var isAnim = false

    val isDepthStateSubject = BehaviorSubject.create<Pair<Boolean, Int>>()
    val deleteFragmentSubject = BehaviorSubject.create<Pair<String, Int>>()
    val addFragmentSubject = BehaviorSubject.create<Pair<String, Int>>()


    fun toggleDepthState(fragmentsCount: Int) {
//        if (isAnim) return //TODO fix bug (Completable merge array don't work)
        isAnim = true
        isDepthState = !isDepthState
        isDepthStateSubject.onNext(Pair(isDepthState, fragmentsCount))
    }

    fun onDeleteFragment(tab: String, number: Int) {
        deleteFragmentSubject.onNext(Pair(tab, number))
    }

    fun depthAnimComplete() {
        isAnim = false
    }

    fun addFragment(tab: String, visibleNumber: Int) {
        addFragmentSubject.onNext(Pair(tab, visibleNumber))
    }
}