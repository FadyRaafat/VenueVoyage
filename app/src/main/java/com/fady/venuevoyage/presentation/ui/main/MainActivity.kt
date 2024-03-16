package com.fady.venuevoyage.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.fady.venuevoyage.R
import com.fady.venuevoyage.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentHome,
                R.id.fragmentProfile,
                R.id.fragmentTermsAndConditions,
                R.id.logout
            ), drawerLayout
        )
        navView.setupWithNavController(navController)
        setObservers(drawerLayout, navView)
    }

    private fun setObservers(drawerLayout: DrawerLayout, navView: NavigationView) {
        mainViewModel.openCloseDrawer.observeForever {
            drawerLayout.isDrawerOpen(navView).let {
                if (it) {
                    closeDrawer()
                } else {
                    drawerLayout.open()
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.deleteUserSuccess.collect { success ->
                if (success) {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.fragmentLogin)
                    closeDrawer()
                }
            }

        }
        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            mainViewModel.deleteUser()
            closeDrawer()
            true
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.close()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}