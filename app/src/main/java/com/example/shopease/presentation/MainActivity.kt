package com.example.shopease.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shopease.R
import com.example.shopease.databinding.ActivityMainBinding
import com.example.shopease.presentation.category.CategoryFragment
import com.example.shopease.presentation.favorite.FavoriteFragment
import com.example.shopease.presentation.home.HomeFragment
import com.example.shopease.presentation.orderHistory.OrderFragment
import com.example.shopease.presentation.profile.ProfileFragment
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNav.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {

                    R.id.btn_home -> {
                        replaceFragment(HomeFragment())
                        true
                    }
                    R.id.btn_category -> {
                        replaceFragment(CategoryFragment())
                        true
                    }

                    R.id.btn_profile -> {
                        replaceFragment(ProfileFragment())
                        true
                    }

                    R.id.btn_favorite -> {
                        replaceFragment(FavoriteFragment())
                        true
                    }

                    R.id.btn_order ->{
                        replaceFragment(OrderFragment())
                        true
                    }


                    else -> false
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }

}


