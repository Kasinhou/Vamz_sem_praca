package com.example.geoguess.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.geoguess.R
import com.example.geoguess.databinding.FragmentReviewBinding

/**
 * Fragment na vyobrazenie vsetkych krajin spolu s hlavnymi mestami
 */
class ReviewFragment : Fragment() {
    private lateinit var binding: FragmentReviewBinding

    private val sharedViewModel: CountryInfoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
    }

    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    /**
     * Pri vytvoreni pohladu sa zavola funkcia na zobrazenie krajin
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewBinding.bind(view)

        showEverything()
    }

    /**
     * Zoradenie vsetkych krajin abecedne a vypisanie na obrazovku
     */
    private fun showEverything() {
        val names: MutableList<String> = mutableListOf()
        var textCountries = ""
        var i = 1

        sharedViewModel.listOfCountries.value?.forEach { it ->
            names.add("${it.name.common} ${it.capital}")
        }
        names.sort()

        names.forEach { it ->
            textCountries += "$i. $it\n"
            ++i
        }
        binding.everything.text = textCountries
    }
}