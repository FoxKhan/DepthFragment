package com.developer.smartfox.fragmentdemo.activity

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeActivityView : MvpView {


    fun animMenu(isDepthState: Boolean)

    fun deleteFragment(tab: String, vNumber: Int)

    fun addFragment(tab: String, vNumber: Int)

    fun replaceFragmentWithBackStack(tab: String, vNumber: Int)

    fun replaceFragment(tab: String, vNumber: Int)

    fun popBackStackByTag(tab: String, vNumber: Int)
}