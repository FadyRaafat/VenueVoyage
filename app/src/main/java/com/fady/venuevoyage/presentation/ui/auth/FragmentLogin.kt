package com.fady.venuevoyage.presentation.ui.auth

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fady.venuevoyage.R
import com.fady.venuevoyage.databinding.FragmentLoginBinding
import com.fady.venuevoyage.presentation.utils.base.BaseFragment
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentLogin : BaseFragment<FragmentLoginBinding, AuthViewModel>() {

    override fun layout(): Int = R.layout.fragment_login

    override val viewModel: AuthViewModel by viewModels()

    override fun FragmentLoginBinding.initializeUI() {
        binding.registerTV.setOnClickListener {
            findNavController().navigate(FragmentLoginDirections.actionFragmentLoginToFragmentRegister())
        }
        binding.loginBTN.mainButton.setOnClickListener {
            login()
        }
    }

    override fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginSuccess.collect { apiSuccess ->
                if (apiSuccess) {
                    findNavController().navigate(FragmentLoginDirections.actionFragmentLoginToFragmentHome())
                }
            }
        }
    }

    private fun login() {
        binding.apply {
            when {
                emailEt.text.toString().isEmpty() -> {
                    emailLay.error = getString(R.string.fill_this_field)
                }

                passwordEt.text.toString().isEmpty() -> {
                    passwordLay.error = getString(R.string.fill_this_field)
                }

                else -> {
                    viewModel.login(
                        email = emailEt.text.toString(),
                        password = passwordEt.text.toString(),
                    )

                }
            }
        }
    }
}