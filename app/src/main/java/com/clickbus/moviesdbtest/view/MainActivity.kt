package com.clickbus.moviesdbtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.clickbus.moviesdbtest.R
import com.clickbus.moviesdbtest.movies.network.ApiCommunicationSingleton
import com.clickbus.moviesdbtest.view.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        ApiCommunicationSingleton.getPopularMovies()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
            }
            R.id.navigation_tv -> {
               Toast.makeText(applicationContext,"TV",Toast.LENGTH_SHORT).show()
            }
            R.id.navigation_account -> {
                Toast.makeText(applicationContext,"Account",Toast.LENGTH_SHORT).show()
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}