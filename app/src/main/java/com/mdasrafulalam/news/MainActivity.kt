package com.mdasrafulalam.news

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mdasrafulalam.news.viewmodel.NewsViewmodel
import com.mdasrafulalam.news.databinding.ActivityMainBinding
import com.mdasrafulalam.news.preference.DataPreference
import com.mdasrafulalam.news.utils.Constants
import com.mdasrafulalam.news.workers.WorkManagerUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.aviran.cookiebar2.CookieBar


val categoryArray = arrayOf(
    "Top News",
    "Business",
    "Entertainment",
    "Science",
    "Sports",
    "Technology",
    "Health"
)

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewmodel
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var preference: DataPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Constants.ISLINEARLYOUT.value = true
        Constants.COUNTRY.value = "us"
        viewModel = ViewModelProvider(this)[NewsViewmodel::class.java]
        preference = DataPreference(applicationContext)
        viewModel.refreshData()
        preference.selectedCountryFlow.asLiveData().observe(this) {
            Constants.COUNTRY.value = it
        }
        WorkManagerUtils().syncData(applicationContext)

        checkPermission()
        if (!Constants.verifyAvailableNetwork(this)) {
            CookieBar.build(this)
                .setTitle(getString(R.string.network_conncetion))
                .setMessage("No Active Internet!")
                .setDuration(5000)
                .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                .show()
        }
        viewModel = ViewModelProvider(this)[NewsViewmodel::class.java]
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navView.itemIconTintList = null
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.newsDetailsFragment || nd.id == R.id.webViewFragment) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.bookMarkFragment -> navController.navigate(R.id.bookMarkFragment)
                else -> navController.navigate(R.id.settingsFragment)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED
        ) {
            CookieBar.build(this)
                .setTitle(getString(R.string.network_conncetion))
                .setTitleColor(R.color.white)
                .setMessage("Internet Permission Required!")
                .setBackgroundColor(R.color.swipe_color_3)
                .setSwipeToDismiss(true)
                .setDuration(5000) // 5 seconds
                .show()
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                0
            )
        }
    }

}
