@file:Suppress("DEPRECATION")

package com.example.unihub

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.unihub.databinding.ActivityMainBinding
import com.example.unihub.utils.NavigationHostProvider
import com.example.unihub.utils.SharedProvider
import java.util.Locale

class MainActivity : AppCompatActivity(), NavigationHostProvider {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navOptions = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true).build()

        binding.bottomNavigationBarMainActivity.setupWithNavController(navController)
        binding.bottomNavigationBarMainActivity.setOnNavigationItemSelectedListener { item ->
            navController.navigate(item.itemId, null, navOptions)
            true
        }

        systemLanguage()
        binding.bottomNavigationBarMainActivity.setOnApplyWindowInsetsListener(null)
        binding.bottomNavigationBarMainActivity.setPadding(0,0,0,0)

        fun AppCompatActivity.fullScreenMode(visible: Boolean) {
            if (visible) {
                // Hide the status bar and navigation bar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.setDecorFitsSystemWindows(false)
                    window.insetsController?.apply {
                        hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
                        systemBarsBehavior =
                            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                } else {
                    // For older versions, use the deprecated method
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
                }
            } else {
                // Show the status bar and navigation bar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.setDecorFitsSystemWindows(true)
                    window.insetsController?.show(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
                } else {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                }
            }
        }

        fullScreenMode(false)
    }

    override fun hideBottomNavigationBar(hiden: Boolean) {
        if (hiden) {
            binding.bottomNavigationBarMainActivity.visibility = View.GONE
        } else {
            binding.bottomNavigationBarMainActivity.visibility = View.VISIBLE
        }
    }

    private fun systemLanguage() {
        when (SharedProvider(this).getLanguage()) {
            "en" -> {
                val locale = Locale("en")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }

            "kk" -> {
                val locale = Locale("kk")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }

            "ru" -> {
                val locale = Locale("ru")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }

            else -> {
                val locale = Locale("kk")
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(config, this.resources.displayMetrics)
            }
        }
    }

    override fun setupBottomNavForRole(isHead: Boolean) {
        val menu = binding.bottomNavigationBarMainActivity.menu
        menu.clear()
        if (isHead) {
            binding.bottomNavigationBarMainActivity.inflateMenu(R.menu.bottom_menu_head)
        } else {
            binding.bottomNavigationBarMainActivity.inflateMenu(R.menu.bottom_menu)
        }
    }
}