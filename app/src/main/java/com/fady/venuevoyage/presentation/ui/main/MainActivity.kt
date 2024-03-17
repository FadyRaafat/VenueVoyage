package com.fady.venuevoyage.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.fady.venuevoyage.R
import com.fady.venuevoyage.databinding.ActivityMainBinding
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.setStatusBarColor
import com.fady.venuevoyage.presentation.utils.common.ViewUtils.toast
import com.fady.venuevoyage.presentation.utils.common.permissionmanager.Permission
import com.fady.venuevoyage.presentation.utils.common.permissionmanager.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val permissionManager by lazy { PermissionManager(WeakReference(this)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.fragmentHome,
                    R.id.fragmentProfile,
                    R.id.fragmentTermsAndConditions,
                    R.id.logout
                ), drawerLayout
            )
            navView.setupWithNavController(findNavController(R.id.nav_host_fragment_content_main))
            setObservers()
            setStatusBarColor(R.color.black)
            checkPermission()
            forceLightTheme()
        }

    }

    private fun ActivityMainBinding.setObservers() {
        mainViewModel.openCloseDrawer.observeForever {
            drawerLayout.isDrawerOpen(navView).let {
                if (it) {
                    closeDrawer()
                } else {
                    openDrawer()
                }
            }
        }
        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            logout()
            true
        }

    }

    private fun logout() {
        mainViewModel.deleteUser()
        lifecycleScope.launch {
            mainViewModel.deleteUserSuccess.collect { success ->
                if (success) {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.fragmentLogin)
                    closeDrawer()
                }
            }

        }
        closeDrawer()
    }

    private fun openDrawer() {
        binding.drawerLayout.open()
    }
    private fun closeDrawer() {
        binding.drawerLayout.close()
    }

    private fun checkPermission() {
        permissionManager.request(Permission.Location).checkPermission { permissions ->
            val allPermissionsGranted = permissions.all { it.value == true }
            if (allPermissionsGranted.not()) {
                toast(R.string.location_permission_is_required)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun forceLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}