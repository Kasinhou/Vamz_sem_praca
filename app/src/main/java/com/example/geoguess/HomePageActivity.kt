package com.example.geoguess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geoguess.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {
    //binding v domovskej obrazovke
    lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_home_page)

        binding.buttonInfo.setOnClickListener{ goToInfo() }
        binding.buttonGuess.setOnClickListener { goToOptions() }
    }

    private fun goToOptions() {
        val Intent = Intent(this, OptionsActivity::class.java)
        startActivity(Intent)
    }

    private fun goToInfo() {
        val Intent = Intent(this, InfoActivity::class.java)
        startActivity(Intent)
    }
}