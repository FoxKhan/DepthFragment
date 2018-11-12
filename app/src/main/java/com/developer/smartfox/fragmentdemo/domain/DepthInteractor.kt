package com.developer.smartfox.fragmentdemo.domain

import com.developer.smartfox.fragmentdemo.model.FragmentInfo
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DepthInteractor @Inject constructor() {

    private var isDepthState = false
    private var isAnim = false

    val isDepthStateSubject = BehaviorSubject.create<Boolean>()
    val deleteFragmentSubject = BehaviorSubject.create<FragmentInfo>()
    val addFragmentSubject = BehaviorSubject.create<FragmentInfo>()
    val popSubject = BehaviorSubject.create<FragmentInfo>()


    fun toggleDepthState() {
//        if (isAnim) return //TODO fix bug (Completable merge array don't work)
        isAnim = true
        isDepthState = !isDepthState
        isDepthStateSubject.onNext(isDepthState)
    }

    fun depthAnimComplete() {
        isAnim = false
    }

    fun addFragment(tab: String, visibleNumber: Int) {
        addFragmentSubject.onNext(FragmentInfo(tab, visibleNumber))
    }

    fun popBackStackFragment(tab: String, visibleNumber: Int) {
        popSubject.onNext(FragmentInfo(tab, visibleNumber))
    }

    fun deleteFragment(tab: String, number: Int) {
        deleteFragmentSubject.onNext(FragmentInfo(tab, number))
    }
}