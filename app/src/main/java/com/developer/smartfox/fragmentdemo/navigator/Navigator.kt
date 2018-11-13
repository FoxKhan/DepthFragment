package com.developer.smartfox.fragmentdemo.navigator

import android.support.v4.app.FragmentManager
import com.developer.smartfox.fragmentdemo.fragment.depth.DepthFragment
import com.developer.smartfox.fragmentdemo.navigator.NavigatorHolder.Companion.ACTIVITY
class Navigator( tab: String) {

    private var containerId: Int? = null
    private var fm: FragmentManager? = null

    private val tab = if (tab == ACTIVITY) "" else "$tab "

    fun initNavigator(containerId: Int, fm: FragmentManager){
        this.containerId = containerId
        this.fm = fm
    }

    fun deInit(){
        this.containerId = null
        this.fm = null
    }

    fun deleteFragment(vNumber: Int) {
        if (fm == null || containerId == null) return
        val fragment = fm!!.findFragmentByTag("$tab$vNumber")
        fragment?.let {
            fm!!.beginTransaction()
                .remove(it)
                .commit()
//            fm.popBackStack()
        }
        //TODO how to remove with clean backStack
    }

    fun addFragment(vNumber: Int, realNumber: Int) {
        if (fm == null || containerId == null) return
        val tag = "$tab$vNumber"
        fm!!.beginTransaction()
//            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right) //lol
//            .setCustomAnimations(R.animator.fragment_in, R.animator.fragment_in)
            .add(containerId!!, DepthFragment.newInstance(tab, vNumber, realNumber), tag)
            .addToBackStack(tag)
//            .setTransition()
            .commit()
    }

    fun replaceFragmentWithBackStack(vNumber: Int, realNumber: Int) {
        if (fm == null || containerId == null) return
        val tag = "$tab$vNumber"
        fm!!.beginTransaction()
            .replace(containerId!!, DepthFragment.newInstance(tab, vNumber, realNumber), tag)
            .addToBackStack(tag)
            .commit()
    }

    fun replaceFragment(vNumber: Int, realNumber: Int) {
        if (fm == null || containerId == null) return
        val tag = "$tab$vNumber"
        fm!!.beginTransaction()
            .replace(containerId!!, DepthFragment.newInstance(tab, vNumber, realNumber), tag)
            .commit()
    }

    fun popBackStackByTag(vNumber: Int) {
        if (fm == null || containerId == null) return
        val tag = "$tab$vNumber"
        fm!!.popBackStack(tag, containerId!!)
    }

    private fun isFinish(): Boolean = fm!!.backStackEntryCount <= 1

    fun popBackStack(): Boolean {
        if (fm == null || containerId == null) return false // maybe
        return if (isFinish()) true
        else {
            fm!!.popBackStack()
            false
        }
    }

}

