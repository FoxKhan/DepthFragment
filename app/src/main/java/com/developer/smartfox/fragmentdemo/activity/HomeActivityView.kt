package com.developer.smartfox.fragmentdemo.activity

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeActivityView : MvpView {


    fun animMenu(isDepthState: Boolean)

    fun selectTab(tab: String)

    fun finishApp()
}