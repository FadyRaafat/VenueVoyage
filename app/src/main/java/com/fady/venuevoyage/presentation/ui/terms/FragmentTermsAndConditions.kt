package com.fady.venuevoyage.presentation.ui.terms

import androidx.fragment.app.viewModels
import com.fady.venuevoyage.R
import com.fady.venuevoyage.databinding.FragmentTermsAndConditionsBinding
import com.fady.venuevoyage.presentation.utils.base.BaseFragment
import com.fady.venuevoyage.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTermsAndConditions :
    BaseFragment<FragmentTermsAndConditionsBinding, MainViewModel>() {

    override fun layout(): Int = R.layout.fragment_terms_and_conditions

    override val viewModel: MainViewModel by viewModels()

    override fun FragmentTermsAndConditionsBinding.initializeUI() {
        binding.mainViewModel = mainVM
        loadTextFileInRawFolder()
    }

    private fun loadTextFileInRawFolder() {
        val inputString = resources.openRawResource(R.raw.termsandconditions).bufferedReader()
            .use { it.readText() }
        binding.termsTV.text = inputString
    }

}