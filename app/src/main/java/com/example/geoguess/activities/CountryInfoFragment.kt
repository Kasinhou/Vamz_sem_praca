package com.example.geoguess.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geoguess.databinding.FragmentCountryInfoBinding
import com.bumptech.glide.Glide
import com.example.geoguess.*
import com.example.geoguess.data.Constants

/**
 * Fragment ktory sa zobrazi ako prvy
 * Ma na starosti posun na ine fragmenty na zaklade stlaceneho buttonu
 */
class CountryInfoFragment : Fragment() {

    //private val sharedViewModel: CountryInfoViewModel by activityViewModels()
    /**
     * Instancia zdielaneho viewmodelu ktory sa inicializuje pomocou lazy, cize sa inicializuje az dokym sa prvykrat nepouzije
     */
    private val sharedViewModel: CountryInfoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
    }

    private lateinit var binding: FragmentCountryInfoBinding

    /**
     * Na zaciatku sa zavola getCountriesProperties na nacitanie krajin
     * pri vytvoreni pohladu sa nastvia reakcie na buttony
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedViewModel.getCountriesProperties()
        Log.i("CountryInfoFragment", "CountryInfoFragment created/")
        binding = FragmentCountryInfoBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = sharedViewModel

        background()

        binding.buttonGuess.setOnClickListener { goToNewFragment(GuessingFragment()) }
        binding.buttonInfo.setOnClickListener { goToNewFragment(WikiFragment()) }
        binding.btnShowEverything.setOnClickListener { goToNewFragment(ReviewFragment()) }
        binding.btnRank.setOnClickListener { goToNewFragment(RankingFragment()) }
        return binding.root
    }

    /**
     * Nacitanie obrazku ako pozadia fragmentu
     */
    private fun background() {
        Glide.with(this)
            .load(Constants.BACKGROUND_INFO_FRAGMENT)
            .into(binding.backgroundMap)
    }

    /**
     * Presun na novy fragment zadany ako parameter
     */
    private fun goToNewFragment(newFragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainerView, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}