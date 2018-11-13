package com.developer.smartfox.fragmentdemo.navigator

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorHolder @Inject constructor() {

    private val navigators = HashMap<String, Navigator>()

    fun getNavigator(tab: String): Navigator {

        var token = false
        navigators.forEach {
            if (it.key == tab) token = true
        }

        return if (token) navigators[tab]!!
        else {
            navigators[tab] = Navigator( tab)
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