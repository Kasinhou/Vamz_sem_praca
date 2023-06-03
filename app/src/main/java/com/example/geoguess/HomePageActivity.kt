package com.example.geoguess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geoguess.data.*
import com.example.geoguess.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {
    //binding v domovskej obrazovke
    lateinit var binding: ActivityHomePageBinding

    private val flags = Flags("https://flagcdn.com/w320/sk.png","https://flagcdn.com/sk.svg","The flag of Slovakia is composed of three equal horizontal bands of white, blue and red. The coat of arms of Slovakia is superimposed at the center of the field slightly towards the hoist side.")
    private val coa = CoatOfArms("https://mainfacts.com/media/images/coats_of_arms/sk.png","https://mainfacts.com/media/images/coats_of_arms/sk.svg")
    private val nam = Name("Slovakia","Slovak Republic",mapOf("slk" to NativeName("Slovenská republika", "Slovensko")))
    private val curr = mapOf("EUR" to Currency("Euro", "€"))
    private val i = Idd("+4", listOf("21"))
    private val cap = listOf("Bratislava")
    private val lan = Languages(mapOf("slk" to "Slovak"))
    private val ll = listOf(48.66666666,19.5)
    private val border = listOf("AUT","CZE","HUN","POL","UKR")
    private val area = 49037.0
    private val maps = Maps("https://goo.gl/maps/uNSH2wW4bLoZVYJj7","https://www.openstreetmap.org/relation/14296")
    private val pop = 5458827
    private val f = "SVK"
    private val t = listOf("UTC+01:00")
    private val con = listOf("Europe")
    private val slovakia = Country(flags, coa, nam, curr, i, cap, lan, ll, border, area, maps, pop, f, t, con)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_home_page)
        println(slovakia.toString())

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