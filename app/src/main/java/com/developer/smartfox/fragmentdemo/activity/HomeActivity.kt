package com.developer.smartfox.fragmentdemo.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.developer.smartfox.fragmentdemo.R
import com.developer.smartfox.fragmentdemo.fragment.childroot.ChildRootFragment
import com.developer.smartfox.fragmentdemo.navigator.NavigatorHolder
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : MvpAppCompatActivity(), HomeActivityView {

    @InjectPresenter
    lateinit var presenter: HomeActivityPresenter

    private val fragmentContainer: Int = R.id.fragment_container

    private val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

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
                    presenter.btmTabClick(NavigatorHolder.SQUARE)
                }
                R.id.navigation_circle -> {
                    presenter.btmTabClick(NavigatorHolder.CIRCLE)
                }
                R.id.navigation_triangle -> {
                    presenter.btmTabClick(NavigatorHolder.TRIANGLE)
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
    override fun selectTab(tab: String) {

        val currentFragment = getCurrentFragment()
        if (currentFragment != null && currentFragment?.tag == tab) return
        if (currentFragment != null) fm.beginTransaction().hide(currentFragment!!).commit()


        val fragment = fm.findFragmentByTag(tab)
        if (fragment == null)
            fm.beginTransaction()
                .add(fragmentContainer, ChildRootFragment.newInstance(tab), tab)
                .commit()
        else fm.beginTransaction()
            .show(fragment)
            .commit()
    }

    private fun getCurrentFragment(): Fragment? {
        fm.fragments.forEach {
            if (it.isVisible) return it
        }
        return null
    }

    override fun onBackPressed() {
        presenter.onBackPressed(getCurrentFragment()?.tag ?: "")
    }

    override fun finishApp(){
        finish()
    }
}
