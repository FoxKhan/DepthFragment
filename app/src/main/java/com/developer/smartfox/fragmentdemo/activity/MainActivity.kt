package com.developer.smartfox.fragmentdemo.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.developer.smartfox.fragmentdemo.R
import com.developer.smartfox.fragmentdemo.fragment.DepthFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), HomeActivityView {

    @InjectPresenter
    lateinit var presenter: HomeActivityPresenter

    private val fragmentContainer: Int = R.id.fragment_container

    private val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        val tab = "square"

        for (i in 1..6) {
            addFragment(tab, i, i)
        }

//        fm.addOnBackStackChangedListener {
//            val backStackEntryCount = fm.backStackEntryCount
//            presenter.onChangeFocusFragment(fm.backStackEntryCount)
//        } // TODO how getEntryCountListener

    }

    override fun animMenu(isDepthState: Boolean) {
        val stateSet = intArrayOf(android.R.attr.state_checked * if (isDepthState) 1 else -1)
        btn_menu.setImageState(stateSet, true)
    }

    private fun initView() {
        navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_square -> {

                }
                R.id.navigation_circle -> {

                }
                R.id.navigation_triangle -> {

                }
            }
            true
        }

        btn_menu.setOnClickListener {
            var visibleFragmentsCount = 0
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment.tag?.contains("square") == true && fragment.isVisible) visibleFragmentsCount++
            }
            presenter.onMenuClick()
        }

    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    //navigator in miniature
    override fun deleteFragment(tab: String, vNumber: Int, realNumber: Int) {
        val fragment = fm.findFragmentByTag("$tab $vNumber")
        fragment?.let {
            fm.beginTransaction()
                .remove(it)
                .commit()
//            fm.popBackStack()
        }
        //TODO how remove with clean backStack
    }

    override fun addFragment(tab: String, vNumber: Int, realNumber: Int) {
        val tag = "$tab $vNumber"
        fm.beginTransaction()
//            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right) //lol
//            .setCustomAnimations(R.animator.fragment_in, R.animator.fragment_in)
            .add(fragmentContainer, DepthFragment.newInstance(tab, vNumber, realNumber), tag)
            .addToBackStack(tag)
//            .setTransition()

            .commit()

    }

    override fun replaceFragmentWithBackStack(tab: String, vNumber: Int, realNumber: Int) {
        val tag = "$tab $vNumber"
        fm.beginTransaction()
            .replace(fragmentContainer, DepthFragment.newInstance(tab, vNumber, realNumber), tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun replaceFragment(tab: String, vNumber: Int, realNumber: Int) {
        val tag = "$tab $vNumber"
        fm.beginTransaction()
            .replace(fragmentContainer, DepthFragment.newInstance(tab, vNumber, realNumber), tag)
            .commit()
    }

    override fun popBackStackByTag(tab: String, vNumber: Int, realNumber: Int) {
        val tag = "$tab $vNumber"
        fm.popBackStack(tag, fragmentContainer)
    }

    override fun onBackPressed() {
        if (fm.backStackEntryCount <= 1) finish()
        super.onBackPressed()
    }
}
