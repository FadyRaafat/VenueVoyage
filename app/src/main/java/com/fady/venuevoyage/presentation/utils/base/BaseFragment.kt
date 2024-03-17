package com.fady.venuevoyage.presentation.utils.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fady.venuevoyage.presentation.ui.main.MainViewModel
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.handleApiError
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.hideLoadingDialog
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.showLoadingDialog

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel?> : Fragment() {

    lateinit var binding: V
    lateinit var toolbarBinding: ViewDataBinding
    protected val mainVM: MainViewModel by activityViewModels()

    protected abstract val viewModel: VM
    private var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFragmentArguments()
        binding.initializeUI()
        setupObservers()
        setUpBaseObservers()
    }


    abstract fun layout(): Int
    private fun setUpBaseObservers() {
        viewModel?.showLoading?.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        viewModel?.showApiError?.observe(viewLifecycleOwner) { failureResource ->
            handleApiError(failureResource)
        }
    }


    override
    fun onResume() {
        super.onResume()
        registerListeners()
    }

    override
    fun onPause() {
        unRegisterListeners()

        super.onPause()
    }

    open fun registerListeners() {}

    open fun unRegisterListeners() {}

    open fun setupObservers() {}

    open fun getFragmentArguments() {}

    open fun V.initializeUI() {}

    private fun showLoading() {
        hideLoading()
        progressDialog = showLoadingDialog(requireActivity(), null)
    }

    fun showLoading(hint: String?) {
        hideLoading()
        progressDialog = showLoadingDialog(requireActivity(), hint)
    }

    fun showLoader() {
        hideLoading()
        progressDialog = showLoadingDialog(requireActivity(), null)
    }

    fun hideLoader() {
        hideLoading()
    }
    private fun hideLoading() = hideLoadingDialog(progressDialog, requireActivity())


    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }



}