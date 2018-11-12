package com.developer.smartfox.fragmentdemo.activity

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeActivityView : MvpView {


    fun animMenu(isDepthState: Boolean)

    @StateStrategyType(SingleStateStrategy::class)
    fun deleteFragment(tab: String, vNumber: Int, realNumber: Int)

    @StateStrategyType(SingleStateStrategy::class)
    fun addFragment(tab: String, vNumber: Int, realNumber: Int)

    @StateStrategyType(SingleStateStrategy::class)
    fun replaceFragmentWithBackStack(tab: String, vNumber: Int, realNumber: Int)

    @StateStrategyType(SingleStateStrategy::class)
    fun replaceFragment(tab: String, vNumber: Int, realNumber: Int)

    @StateStrategyType(SingleStateStrategy::class)
    fun popBackStackByTag(tab: String, vNumber: Int, realNumber: Int)
}