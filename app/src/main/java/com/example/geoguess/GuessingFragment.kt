package com.example.geoguess

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geoguess.activities.CountryInfoViewModel
import com.example.geoguess.databinding.FragmentGuessingBinding


class GuessingFragment : Fragment(R.layout.fragment_guessing) {
    private lateinit var binding: FragmentGuessingBinding

    private lateinit var sharedViewModel: CountryInfoViewModel

    private var count : Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGuessingBinding.bind(view)

        sharedViewModel = ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
        binding.buttonTry.setOnClickListener { spracuj() }
    }

    private fun spracuj() {
        val guess = binding.searchedItem.text.toString().lowercase()
        if (sharedViewModel.getCountry(guess) != null) {
            count += 1
            binding.tvWrong.setBackgroundColor(Color.WHITE)
            binding.tvCorrect.setBackgroundColor(Color.GREEN)
            binding.tvCorrectCount.text = "$count/${sharedViewModel.countries.size}"
            //binding.tvCorrectCount.text = "$count/${sharedViewModel.listOfCountries.value?.size}"
        } else {
            binding.tvCorrect.setBackgroundColor(Color.WHITE)
            binding.tvWrong.setBackgroundColor(Color.RED)
        }
        binding.searchedItem.setText("")
        //binding.root.setBackgroundColor(Color.RED)
    }
}