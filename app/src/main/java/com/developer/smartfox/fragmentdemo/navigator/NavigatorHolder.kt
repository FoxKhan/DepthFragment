package com.developer.smartfox.fragmentdemo.navigator

import android.support.v4.app.FragmentManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorHolder @Inject constructor() {

    private val navigators = HashMap<String, Navigator>()

    fun getNavigator(container: Int, fm: FragmentManager, tab: String): Navigator {

        var token = false
        navigators.forEach {
            if (it.key == tab) token = true
        }

        return if (token) navigators[tab]!!
        else {
            navigators[tab] = Navigator(container, fm, tab)
            navigators[tab]!!
        }
    }


    companion object {
        const val ACTIVITY = "activity"
        const val SQUARE = "square"
        const val CIRCLE = "circle"
        const val TRIANGLE = "triangle"
    }
}