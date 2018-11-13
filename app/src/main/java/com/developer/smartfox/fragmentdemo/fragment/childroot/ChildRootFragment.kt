package com.developer.smartfox.fragmentdemo.fragment.childroot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.developer.smartfox.fragmentdemo.App
import com.developer.smartfox.fragmentdemo.R
import com.developer.smartfox.fragmentdemo.navigator.Navigator

class ChildRootFragment : MvpAppCompatFragment() , ChildRootFragmentView {

    @InjectPresenter
    lateinit var presenter: ChildRootFragmentPresenter

    @ProvidePresenter
    fun providePresenter(): ChildRootFragmentPresenter {

        val navigator: Navigator
        if (arguments != null && arguments?.getString(TAB) != null) {
            navigator = navHolder.getNavigator(
                R.id.fragment_container, childFragmentManager,
                arguments?.getString(TAB)!!
            )
        } else throw Exception("tab arg is absent")

        return ChildRootFragmentPresenter(navigator, arguments?.getString(TAB)!!)
    }

    private val navHolder = App.graph.navigatorHolder

    lateinit var root: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_child_root, container, false)

    companion object {
        const val TAB = "tab"

        fun newInstance(tab: String): ChildRootFragment {
            val fragment = ChildRootFragment()
            val args = Bundle()
            args.putString(TAB, tab)
            fragment.arguments = args
            return fragment
        }
    }
}