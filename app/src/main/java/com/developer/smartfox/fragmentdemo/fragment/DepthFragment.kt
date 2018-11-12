package com.developer.smartfox.fragmentdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.developer.smartfox.fragmentdemo.R
import com.developer.smartfox.fragmentdemo.common.KotlinTransitionHelper
import com.developer.smartfox.fragmentdemo.common.plusAssign
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_depth.view.*

class DepthFragment : MvpAppCompatFragment(), DepthFragmentView {

    @InjectPresenter
    lateinit var presenter: DepthFragmentPresenter

    private val cd = CompositeDisposable()

    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_depth, container, false)
        setArgs()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.f_btn_add.setOnClickListener {
            presenter.addFragmentClick()
        }

        view.f_btn_delete.setOnClickListener {
            presenter.deleteFragmentClick()
        }

        view.f_btn_pop.setOnClickListener {
            presenter.popFragmentClick()
        }
    }

    override fun animDepth(isDepthState: Boolean, fragmentNumber: Int) {
        if (fragmentNumber == -1) return
        cd += if (isDepthState) {
            KotlinTransitionHelper.startMenuAnimate(root, fragmentNumber)
                .subscribe { presenter.depthAnimComplete() }
        } else {
            KotlinTransitionHelper.startRevertFromMenu(root, getFCont("square"), fragmentNumber)
                .subscribe { presenter.depthAnimComplete() }
        }
    }

    override fun setFragmentNumber(number: String) {
        root.f_txt_number.text = number
    }


    private fun setArgs() {
        presenter.fragmentTab = arguments?.getString(TAB) ?: ""
        presenter.fragmentVisibleNumber = arguments?.getInt(NUMBER) ?: -1
        presenter.fragmentRealNumber = arguments?.getInt(NUMBER) ?: -1
    }


    override fun onStop() {
        super.onStop()
        cd.dispose()
        cd.clear()
    }

    private fun getFCont(tag: String): Int {
        var count = 0
        activity!!.supportFragmentManager.fragments.forEach {
            if (it.tag == tag && it.isVisible) count++
        }
        return count
    }

    companion object {
        const val TAB = "tab"
        const val NUMBER = "number"

        fun newInstance(tab: String, number: Int): DepthFragment {

            val args = Bundle()
            args.putString(TAB, tab)
            args.putInt(NUMBER, number)

            val fragment = DepthFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
