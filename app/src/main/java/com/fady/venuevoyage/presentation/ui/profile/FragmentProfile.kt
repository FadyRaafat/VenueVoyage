package com.fady.venuevoyage.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fady.venuevoyage.R
import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.databinding.FragmentProfileBinding
import com.fady.venuevoyage.presentation.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentProfile : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override fun layout(): Int = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModels()

    override fun FragmentProfileBinding.initializeUI() {
        binding.mainViewModel = mainVM
        viewModel.getUserProfile()
    }

    override fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUserProfileSuccess.collect { userProfile ->
                setData(userProfile)
            }
        }
    }

    private fun setData(userProfile: UserProfile) {
        binding.apply {
            userProfile.let {
                firstNameEt.setText(userProfile.firstName ?: "")
                lastNameEt.setText(userProfile.lastName)
                ageEt.setText(userProfile.age.toString())
                emailEt.setText(userProfile.email)
            }
        }
    }
}