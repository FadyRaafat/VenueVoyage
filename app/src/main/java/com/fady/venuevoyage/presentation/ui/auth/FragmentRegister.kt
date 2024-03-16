package com.fady.venuevoyage.presentation.ui.auth

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fady.venuevoyage.R
import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.databinding.FragmentRegisterBinding
import com.fady.venuevoyage.presentation.utils.base.BaseFragment
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.isValidEmail
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.isValidPassword
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentRegister : BaseFragment<FragmentRegisterBinding, AuthViewModel>() {

    override fun layout(): Int = R.layout.fragment_register

    override val viewModel: AuthViewModel by viewModels()

    override fun FragmentRegisterBinding.initializeUI() {
        binding.apply {
            loginTV.setOnClickListener {
                findNavController().popBackStack()
            }
            registerBTN.mainButton.setOnClickListener {
                register()
            }
        }
    }

    override fun setupObservers() {
        binding.apply {
            ageEt.doAfterTextChanged {
                if (it.toString().toInt() < 18) {
                    ageLay.error = getString(R.string.age_error)
                } else {
                    ageLay.error = null
                }

            }
            passwordEt.doAfterTextChanged {
                if (it.toString().isValidPassword().not()) {
                    passwordLay.error = getString(R.string.password_error)
                } else {
                    passwordLay.error = null
                }
            }
            emailEt.doAfterTextChanged {
                if (it.toString().isValidEmail().not()) {
                    emailLay.error = getString(R.string.email_error)
                } else {
                    emailLay.error = null
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.saveUserSuccess.collect { apiSuccess ->
                if (apiSuccess) {
                    requireContext().toast(R.string.registered_success)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun register() {
        binding.apply {
            when {
                firstNameEt.text.toString().isEmpty() -> {
                    firstNameLay.error = getString(R.string.fill_this_field)
                }

                lastNameEt.text.toString().isEmpty() -> {
                    lastNameLay.error = getString(R.string.fill_this_field)
                }

                emailEt.text.toString().isEmpty() -> {
                    emailLay.error = getString(R.string.fill_this_field)
                }

                passwordEt.text.toString().isEmpty() -> {
                    passwordLay.error = getString(R.string.fill_this_field)
                }

                ageEt.text.toString().isEmpty() -> {
                    ageLay.error = getString(R.string.fill_this_field)
                }

                confirmPasswordEt.text.toString().isEmpty() -> {
                    confirmPasswordLay.error = getString(R.string.fill_this_field)
                }

                passwordEt.text.toString() != confirmPasswordEt.text.toString() -> {
                    confirmPasswordLay.error = getString(R.string.must_confirm_password)
                }

                ageEt.text.toString().toInt() < 18 -> {
                    ageLay.error = getString(R.string.age_error)
                }

                passwordEt.text.toString().isValidPassword().not() -> {
                    passwordLay.error = getString(R.string.password_error)
                }

                emailEt.text.toString().isValidEmail().not() -> {
                    emailLay.error = getString(R.string.email_error)
                }

                else -> {
                    viewModel.register(
                        UserProfile(
                            firstName = firstNameEt.text.toString(),
                            lastName = lastNameEt.text.toString(),
                            email = emailEt.text.toString(),
                            password = passwordEt.text.toString(),
                            age = ageEt.text.toString().toInt()
                        )
                    )
                }
            }
        }
    }
}