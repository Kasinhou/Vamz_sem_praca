package com.example.geoguess.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.geoguess.databinding.ActivityHomePageBinding

/**
 * Hlavna obrazovka aplikacie, main
 */
class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding

    /**
     * Pri vytvoreni sa skryje action bar
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        //setContentView(R.layout.activity_home_page)

        Log.i("HomePageActivity", "ON CREATE")
    }

    override fun onStart() {
        super.onStart()
        Log.i("HomePageActivity", "ON START")
    }
}