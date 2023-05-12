package com.example.geoguess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.geoguess.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_options)

        binding.buttonCapital.setOnClickListener{ goToQuiz() } // parameter nieco cim odlisim capital a country
        binding.buttonCountry.setOnClickListener { goToQuiz() }
        //glide kniznica na obrazky
    }

    private fun goToQuiz() {
        val Intent = Intent(this, QuizActivity::class.java)
        startActivity(Intent)
    }
}