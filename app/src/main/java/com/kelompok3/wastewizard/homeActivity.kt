package com.kelompok3.wastewizard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kelompok3.wastewizard.databinding.ActivityHomeBinding
import com.kelompok3.wastewizard.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_bar -> replaceFragment(HomeFragment())
                R.id.profile_bar -> replaceFragment(ProfileFragment())
                R.id.news_bar -> replaceFragment(NewsFragment())

                else ->{

                }
            }
            true
        }

    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.flfragment)
        if (currentFragment !is HomeFragment) {
            replaceFragment(HomeFragment())
            binding.bottomNavigationView.selectedItemId = R.id.home_bar
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flfragment, fragment)
        when(fragment){
            is HomeFragment -> binding.bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.background_below))
            is ProfileFragment -> binding.bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.background_profile))
            is NewsFragment -> binding.bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.background_news))
        }
        fragmentTransaction.commit()
    }
}