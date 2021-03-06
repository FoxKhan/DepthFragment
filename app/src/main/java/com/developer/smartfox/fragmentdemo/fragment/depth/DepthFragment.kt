package com.developer.smartfox.fragmentdemo.fragment.depth

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.developer.smartfox.fragmentdemo.R
import com.developer.smartfox.fragmentdemo.common.KotlinTransitionHelper
import com.developer.smartfox.fragmentdemo.common.plusAssign
import com.developer.smartfox.fragmentdemo.navigator.NavigatorHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_depth.view.*

class DepthFragment : MvpAppCompatFragment(), DepthFragmentView {

    @InjectPresenter
    lateinit var presenter: DepthFragmentPresenter

    private val cd = CompositeDisposable()

    lateinit var fab: FloatingActionButton

    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_depth, container, false)
        setArgs()

//        val target = root.findViewById<View>(R.id.fragment_root) as DepthLayout
//        val dp = target.resources.displayMetrics.density
//        target.also {
//            it.scaleX = 0.5f
//            it.scaleY = 0.5f
//            it.rotationX = 60f
//            it.rotation = -50f
//            it.translationY = 10 * dp
//            it.customShadowElevation = 10 * dp //todo check it (cable is non)
//        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fab = view.f_btn_add

        view.f_btn_add.setOnClickListener {
            presenter.addFragmentClick(getFCont())
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
            KotlinTransitionHelper.startRevertFromMenu(root, getFCont(), fragmentNumber)
                .subscribe { presenter.depthAnimComplete() }
        }
    }

    override fun setFragmentNumber(number: String) {
        root.f_txt_number.text = number
    }


    private fun setArgs() {
        presenter.fragmentTab = arguments?.getString(TAB) ?: ""
        presenter.fragmentTagNumber = arguments?.getInt(NUMBER) ?: -1
        presenter.fragmentRealNumber = arguments?.getInt(REAL_NUMBER) ?: -1
    }


    override fun onStop() {
        super.onStop()
        cd.dispose()
        cd.clear()
    }

    override fun onResume() {
        super.onResume()
        Log.d("SDS", "onResume: $tag")
    }

    private fun getFCont(): Int {
        if (tag!!.contains(NavigatorHolder.CIRCLE)) return 0
        return parentFragment!!.childFragmentManager.fragments.size
    }

    override fun setAddBtn(isEnable: Boolean) {
        fab.isEnabled = isEnable
    }

    companion object {
        const val TAB = "tab"
        const val NUMBER = "number"
        const val REAL_NUMBER = "realNumber"

        fun newInstance(tab: String, number: Int, realNumber: Int): DepthFragment {

            val args = Bundle()
            args.putString(TAB, tab)
            args.putInt(NUMBER, number)
            args.putInt(REAL_NUMBER, realNumber)

            val fragment = DepthFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
