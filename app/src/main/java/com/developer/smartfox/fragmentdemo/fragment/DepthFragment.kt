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

        view.fragment_btn_add.setOnClickListener {
            presenter.addFragmentClick()
        }
    }

    override fun animDepth(isDepthState: Pair<Boolean, Int>, fragmentNumber: Int) {
        if (fragmentNumber == -1) return
        cd += if (isDepthState.first) {
            KotlinTransitionHelper.startMenuAnimate(root, isDepthState.second, fragmentNumber)
                .subscribe { presenter.depthAnimComplete() }
        } else {
            KotlinTransitionHelper.startRevertFromMenu(root, isDepthState.second, fragmentNumber)
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
