package com.example.geoguess

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geoguess.activities.CountryInfoViewModel
import com.example.geoguess.databinding.FragmentGuessingBinding


class GuessingFragment : Fragment(R.layout.fragment_guessing) {
    private lateinit var binding: FragmentGuessingBinding

    private lateinit var sharedViewModel: CountryInfoViewModel

    private var timeStart: Long = 0
    private var timeEnd: Long = 0

    private var count : Int = 0
    private var hasStarted : Boolean = false
    private var guessedCountries: MutableList<String> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGuessingBinding.bind(view)

        sharedViewModel = ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)

        binding.buttonStart.setOnClickListener { startQuiz() }

        binding.buttonTry.setOnClickListener { checkGuess() }
        binding.buttonEnd.setOnClickListener { endOfQuiz() }
    }

    private fun startQuiz() {
        hasStarted = true
        timeStart = System.nanoTime()
    }

    private fun endOfQuiz() {
        if (!hasStarted)
            return
        timeEnd = System.nanoTime()
        hasStarted = false
        guessedCountries.clear()
        binding.tvWrong.setBackgroundColor(Color.WHITE)
        binding.tvCorrect.setBackgroundColor(Color.WHITE)
        binding.tvTime.text = "Time: ${(timeEnd - timeStart)/1000000000} seconds and count: $count"
        count = 0
    }

    private fun checkGuess() {
        if (!hasStarted)
            return

        val guess = binding.searchedItem.text.toString().lowercase().trimEnd()
        val guessedCountry = sharedViewModel.getCountry(guess)
        if (guess in guessedCountries) {
            binding.tvWrong.setBackgroundColor(Color.WHITE)
            binding.tvCorrect.setBackgroundColor(Color.GREEN)
            binding.searchedItem.setText("")
        } else if (guessedCountry != null) {
            count += 1
            binding.tvWrong.setBackgroundColor(Color.WHITE)
            binding.tvCorrect.setBackgroundColor(Color.GREEN)
            binding.tvCorrectCount.text = "$count/${sharedViewModel.listOfCountries.value?.size}"
            guessedCountries.add(guess)
            binding.searchedItem.setText("")
        } else {
            binding.tvCorrect.setBackgroundColor(Color.WHITE)
            binding.tvWrong.setBackgroundColor(Color.RED)
        }
        //binding.searchedItem.setText("")
        //binding.root.setBackgroundColor(Color.RED)
    }
}