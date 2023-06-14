package com.example.geoguess.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.geoguess.databinding.FragmentWikiBinding
import com.bumptech.glide.Glide
import com.example.geoguess.R

/**
 * Fragment ktory ma na starosti spristupnovat informacie o zadanej krajine
 */
class WikiFragment : Fragment(R.layout.fragment_wiki) {
    private lateinit var binding: FragmentWikiBinding

    private val sharedViewModel: CountryInfoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
    }

    /**
     * Vytvorenie reakcie na buttony
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWikiBinding.bind(view)

        //sharedViewModel = ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
        binding.buttonSearch.setOnClickListener { show() }
        binding.buttonClearInfo.setOnClickListener {
            binding.searchedCountry.setText("")
            clearInfo()
        }
    }

    /**
     * Vycistenie informacii a obrazkov z fragmentu
     */
    private fun clearInfo() {
        binding.tvCountryName.text = ""
        binding.tvInfoAboutCountry.text = ""
        binding.ivCoA.visibility = View.GONE
        binding.ivFlag.visibility = View.GONE
    }

    /**
     * Ulozenie stavu fragmentu
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("countryName", binding.searchedCountry.text.toString())
        outState.putBoolean("notShowed", binding.tvCountryName.text.equals(""))
    }

    /**
     * Nacitanie stavu po opatovnom vytvoreni pohladu
     * pri rotacii zariadenia sa stav nezmeni, zobrazenie zostane rovnake
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            binding.searchedCountry.setText(savedInstanceState.getString("countryName"))
            if (!savedInstanceState.getBoolean("notShowed"))
                show()
        }
    }

    /**
     * Zobrazenie vsetkych informacii krajiny ak jej nazov bol zadany spravne
     */
    private fun show() {
        val text = binding.searchedCountry.text.toString().lowercase().trimEnd().trimStart()

        if (sharedViewModel.getCountry(text) != null) {
            showImages(text)
            binding.tvCountryName.text = text.uppercase()
            binding.tvInfoAboutCountry.text = sharedViewModel.getCountry(text)?.showInfo()
        } else {
            Toast.makeText(requireContext(), "Wrong name of country.\nTry again.", Toast.LENGTH_SHORT).show()
            clearInfo()
        }
    }

    /**
     * Zobrazenie obrazkov cez url adresu do konkretnych imageViews
     */
    private fun showImages(name: String) {
        val flagUrl = sharedViewModel.getCountry(name)?.flags?.png.toString()
        val coaUrl = sharedViewModel.getCountry(name)?.coatOfArms?.png.toString()
        binding.ivCoA.visibility = View.VISIBLE
        binding.ivFlag.visibility = View.VISIBLE

        Glide.with(this)
            .load(flagUrl)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(binding.ivFlag)

        Glide.with(this)
            .load(coaUrl)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(binding.ivCoA)

    }
}