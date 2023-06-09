package com.example.geoguess.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.geoguess.data.Constants
import com.example.geoguess.R
import com.example.geoguess.databinding.FragmentGuessingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.ArrayList

/**
 * Fragment, ktory sa stara o kviz
 * Uchovava informacie o pocte uhadnutych, stopuje cas uzivatelovi
 */
class GuessingFragment : Fragment(R.layout.fragment_guessing) {
    private lateinit var binding: FragmentGuessingBinding

    private val sharedViewModel: CountryInfoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
    }

    private var timeStart: Long = 0
    private var timeEnd: Long = 0

    private var count : Int = 0
    private var hasStarted : Boolean = false
    private var guessedCountries: MutableList<String> = mutableListOf()

    /**
     * Vytvorenie reakci na buttony, nastavenie pozadia
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGuessingBinding.bind(view)

        //sharedViewModel = ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
        binding.tvCorrectCount.text = "$count/${sharedViewModel.listOfCountries.value?.size}"

        background()

        binding.buttonStart.setOnClickListener { startQuiz() }
        binding.buttonTry.setOnClickListener { checkGuess() }
        binding.buttonEnd.setOnClickListener { endOfQuiz() }
        binding.buttonClear.setOnClickListener { clearText() }
    }

    /**
     * Ulozenie stavu fragmentu
     * pri otoceni obrazovky sa informacie nestratia
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", count)
        outState.putLong("timeStart", timeStart)
        outState.putBoolean("hasStarted", hasStarted)
        outState.putString("textWritten", binding.searchedItem.text.toString())
        outState.putStringArrayList("guessedCountries", ArrayList(guessedCountries))
        outState.putInt("colorCorrect", binding.tvCorrect.currentTextColor)
        outState.putInt("colorWrong", binding.tvWrong.currentTextColor)
    }

    /**
     * NAcitanie stavu
     * pri otoceni obrazovky sa nacitaju ulozene informacie a obnovi sa pohlad
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count")
            timeStart = savedInstanceState.getLong("timeStart")
            hasStarted = savedInstanceState.getBoolean("hasStarted")
            binding.searchedItem.setText(savedInstanceState.getString("textWritten"))
            binding.tvCorrectCount.text = "$count/${sharedViewModel.listOfCountries.value?.size}"
            guessedCountries = savedInstanceState.getStringArrayList("guessedCountries")?.toMutableList() ?: mutableListOf()
            binding.tvCorrect.setTextColor(savedInstanceState.getInt("colorCorrect"))
            binding.tvWrong.setTextColor(savedInstanceState.getInt("colorWrong"))
        }
    }

    /**
     * Vymaze sa cely text
     */
    private fun clearText() {
        binding.searchedItem.setText("")
    }

    /**
     * Nastavi sa pozadie obrazovky pomocou obrazku z url adresy
     */
    private fun background() {
        Glide.with(this)
            .load(Constants.BACKGROUND_GUESSING_FRAGMENT)
            .into(binding.backgroundGuess)
    }

    /**
     * Zaciatok kvizu
     * Ak kviz este nezacal, inicializuju sa hodnoty na pociatocne
     */
    private fun startQuiz() {
        if (!hasStarted) {
            Toast.makeText(requireContext(), "Quiz starts. Good luck", Toast.LENGTH_SHORT).show()
            hasStarted = true
            timeStart = System.nanoTime()
            count = 0
            clearText()
        }
    }

    /**
     * Ukoncenie kvizu
     * ak kviz zacal, UX sa nastavi na neutralne hodnoty a zavola sa dialogove okno
     */
    private fun endOfQuiz() {
        if (!hasStarted)
            return
        timeEnd = System.nanoTime()
        hasStarted = false
        guessedCountries.clear()
        binding.tvWrong.setTextColor(Color.BLACK)
        binding.tvCorrect.setTextColor(Color.BLACK)
        clearText()
        showEndDialog()
    }

    /**
     * Zobrazi sa okno s informaciami o case kvizu a pocte uhadnutych krajin
     * pouzivatel ma moznost skoncit alebo zacat kviz od zaciatku
     */
    private fun showEndDialog() {
        val time = (timeEnd - timeStart)/1000000000
        val min = time/60
        val sec = time%60
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("CONGRATS!")
            .setMessage("It took you $min minutes and $sec seconds to guess $count countries.")
            .setCancelable(false)
            .setNegativeButton("EXIT") { _, _ -> goToHomePage(CountryInfoFragment(), min, sec) }
            .setPositiveButton("TRY AGAIN") {_, _ -> tryAgain(min, sec)}
            .setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.dialog))
            .show()

    }

    /**
     * Ulozi sa skore a kviz zacne od zaciatku
     */
    private fun tryAgain(min: Long, sec: Long) {
        saveScore(min, sec)
        startQuiz()
    }

    /**
     * Ulozi sa skore a prejde sa na zaciatocny fragment, domovsku obrazovku
     */
    private fun goToHomePage(newFragment: Fragment, min: Long, sec: Long) {
        saveScore(min, sec)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainerView, newFragment)
        fragmentTransaction.commit()
    }

    /**
     * Vyskocenie okna s moznostou zadania mena na ulozenie vysledkov
     */
    private fun saveScore(min: Long, sec: Long) {
        val popUpWindow = PopUpFragment.instance(min, sec, count)
        popUpWindow.show((activity as AppCompatActivity).supportFragmentManager, "Pop Up Window")
    }

    /**
     * Kontrola spravnosti zadaneho nazvu krajiny a nasledne oznamenie pouzivatelovi podla vysledku
     * bud sa pripocitaju body a rozsvieti sa napis correct alebo sa rozsvieti napis wrong
     * ak krajina existuje, prida sa do zoznamu uhadnutych krajin
     */
    private fun checkGuess() {
        if (!hasStarted) {
            Toast.makeText(requireContext(), "Quiz has not started yet!", Toast.LENGTH_LONG).show()
            return
        }

        val guess = binding.searchedItem.text.toString().lowercase().trimEnd()
        val guessedCountry = sharedViewModel.getCountry(guess)
        if (guess in guessedCountries) {
            Toast.makeText(requireContext(), "Country already guessed.", Toast.LENGTH_SHORT).show()
            binding.tvWrong.setTextColor(Color.BLACK)
            binding.tvCorrect.setTextColor(Color.GREEN)
            clearText()
        } else if (guessedCountry != null) {
            count += 1
            binding.tvWrong.setTextColor(Color.BLACK)
            binding.tvCorrect.setTextColor(Color.GREEN)
            binding.tvCorrectCount.text = "$count/${sharedViewModel.listOfCountries.value?.size}"
            guessedCountries.add(guess)
            clearText()
            if (count == sharedViewModel.listOfCountries.value?.size)
                endOfQuiz()
        } else {
            binding.tvCorrect.setTextColor(Color.BLACK)
            binding.tvWrong.setTextColor(Color.RED)
        }
    }
}