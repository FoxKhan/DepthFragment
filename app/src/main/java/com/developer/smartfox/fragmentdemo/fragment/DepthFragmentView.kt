package com.developer.smartfox.fragmentdemo.fragment

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface DepthFragmentView : MvpView {

    fun animDepth(isDepthState: Pair<Boolean, Int>, fragmentNumber : Int)

    fun setFragmentNumber(number : String)
}