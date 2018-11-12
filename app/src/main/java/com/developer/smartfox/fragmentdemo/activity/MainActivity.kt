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

    private val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        val tab = "square"

        for (i in 1..6) {
            addFragment(tab, i)
        }

    }

    override fun animMenu(pair: Pair<Boolean, Int>) {
        val stateSet = intArrayOf(android.R.attr.state_checked * if (pair.first) 1 else -1)
        btn_menu.setImageState(stateSet, true)
    }

    private fun initView() {
        navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_square -> {

                }
                R.id.navigation_circle -> {

                }
                R.id.navigation_triangle -> {
                    val fragment = supportFragmentManager.findFragmentByTag("square 2")
                    supportFragmentManager.beginTransaction().remove(fragment!!).commit()
                    presenter.onFragmentDelete("square", 2)
                }
            }
            true
        }

        btn_menu.setOnClickListener {
            var visibleFragmentsCount = 0
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment.tag?.contains("square") == true && fragment.isVisible) visibleFragmentsCount++
            }
            presenter.onMenuClick(visibleFragmentsCount)
        }

    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }


    //navigator in miniature
    override fun deleteFragment(tab: String, vNumber: Int) {
        val fragment = fm.findFragmentByTag("$tab $vNumber")
        fragment?.let { fm.beginTransaction().remove(it).commit() }
    }

    override fun addFragment(tab: String, vNumber: Int) {
        val tag = "$tab $vNumber"
        fm.beginTransaction()
            .add(R.id.fragment_container, DepthFragment.newInstance(tab, vNumber), tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun replaceFragmentWithBackStack(tab: String, vNumber: Int) {
        val tag = "$tab $vNumber"
        fm.beginTransaction()
            .replace(R.id.fragment_container, DepthFragment.newInstance(tab, vNumber), tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun replaceFragment(tab: String, vNumber: Int) {
        val tag = "$tab $vNumber"
        fm.beginTransaction()
            .replace(R.id.fragment_container, DepthFragment.newInstance(tab, vNumber), tag)
            .commit()
    }

    override fun popBackStackByTag(tab: String, vNumber: Int) {
        val tag = "$tab $vNumber"
        fm.popBackStack(tag, R.id.fragment_container)
    }
}
