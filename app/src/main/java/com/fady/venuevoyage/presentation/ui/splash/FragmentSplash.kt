package com.fady.venuevoyage.presentation.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fady.venuevoyage.R
import com.fady.venuevoyage.databinding.FragmentSplashBinding
import com.fady.venuevoyage.presentation.ui.auth.AuthViewModel
import com.fady.venuevoyage.presentation.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSplash : BaseFragment<FragmentSplashBinding, AuthViewModel>() {

    override fun layout(): Int = R.layout.fragment_splash

    override val viewModel: AuthViewModel by viewModels()

    override fun FragmentSplashBinding.initializeUI() {
        viewModel.isLoggedIn()
    }

    override fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoggedIn.collect { apiSuccess ->
                delay(1500)
                if (apiSuccess) {
                    findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentHome())
                } else {
                    findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentLogin())

                }
            }
        }
    }
}