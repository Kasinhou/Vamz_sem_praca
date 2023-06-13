package com.example.geoguess

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.geoguess.activities.CountryInfoFragment
import com.example.geoguess.activities.CountryInfoViewModel
import com.example.geoguess.databinding.FragmentGuessingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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

        background()

        binding.buttonStart.setOnClickListener { startQuiz() }
        binding.buttonTry.setOnClickListener { checkGuess() }
        binding.buttonEnd.setOnClickListener { endOfQuiz() }
    }

    private fun background() {
        Glide.with(this)
            .load("https://e0.pxfuel.com/wallpapers/284/1003/desktop-wallpaper-world-map-mint-green-iphone-map-world-map-aesthetic-world.jpg")
            .into(binding.backgroundGuess)
    }

    private fun startQuiz() {
        hasStarted = true
        timeStart = System.nanoTime()
        count = 0
        binding.tvCorrectCount.text = "$count/${sharedViewModel.listOfCountries.value?.size}"
    }

    private fun endOfQuiz() {
        if (!hasStarted)
            return
        timeEnd = System.nanoTime()
        hasStarted = false
        guessedCountries.clear()
        binding.tvWrong.setBackgroundColor(Color.WHITE)
        binding.tvCorrect.setBackgroundColor(Color.WHITE)
        binding.searchedItem.setText("")
        //var time = (timeEnd - timeStart)/1000000000
        //binding.tvTime.text = "Time: $time seconds and count: $count"
        showEndDialog()
    }

    private fun showEndDialog() {
        val time = (timeEnd - timeStart)/1000000000
        val min = time/60
        val sec = time%60
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("CONGRATS!")
            .setMessage("It took you $min minutes and $sec seconds to guess $count countries.")
            .setCancelable(false)
            .setNegativeButton("EXIT") { _, _ -> goToHomePage(CountryInfoFragment()) }
            .setPositiveButton("TRY AGAIN") {_, _ -> startQuiz()}
            .setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.dialog))
            .show()

    }

    private fun goToHomePage(newFragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainerView, newFragment)
        fragmentTransaction.commit()
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
            if (count == sharedViewModel.listOfCountries.value?.size)
                endOfQuiz()
        } else {
            binding.tvCorrect.setBackgroundColor(Color.WHITE)
            binding.tvWrong.setBackgroundColor(Color.RED)
        }
    }
}