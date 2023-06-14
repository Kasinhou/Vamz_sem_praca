package com.example.geoguess.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geoguess.data.Database
import com.example.geoguess.R
import com.example.geoguess.databinding.FragmentRankingBinding
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Fragment na celkove hodnotenie kvizu s moznostou nastavenia podla coho sa utriedi
 */
class RankingFragment : Fragment() {
    private lateinit var binding: FragmentRankingBinding

    /**
     * vytvorenie pohladu
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    /**
     * Nastavenie reakcie na buttony
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRankingBinding.bind(view)

        binding.btnAvgGuessed.setOnClickListener { rankUsers(true) }
        binding.btnMaxCount.setOnClickListener { rankUsers(false) }
    }

    /**
     * Nacitanie dat z databazy a pracovanie s nimi
     * Zoradenie a vypisanie celkoveho poradia
     * podla parametra
     * bud zoradene od najvacsieho poctu uhadnutych alebo od najnizsieho priemeru na jedno uhadnutie
     */
    private fun rankUsers(byAvg: Boolean) {
        val data = Database(requireContext()).load()
        val rankings: MutableMap<Double, String> = mutableMapOf()
        val sortRanking: MutableMap<Double, String>
        var rankedUsers = ""
        var i = 1

        if (byAvg) {
            data.forEach { it ->
                if (it.count == 0)
                    rankings[1000.0] = "${it.user}\n     time: ${it.min}min and ${it.sec}sec, guessed: ${it.count}"
                else
                    rankings[((it.sec + (it.min * 60.0)) / it.count)] =
                        "${it.user}\n     time: ${it.min}min and ${it.sec}sec, guessed: ${it.count}"
            }
            sortRanking = rankings.toSortedMap()
        } else {
            data.forEach { it ->
                rankings[it.count.toDouble()] = "${it.user}\n     time: ${it.min}min and ${it.sec}sec, guessed: ${it.count}"
            }
            sortRanking = rankings.toSortedMap(compareByDescending { it })
        }

        for ((key, value) in sortRanking) {
            rankedUsers += "$i. $value, ratio: ${BigDecimal(key).setScale(2, RoundingMode.CEILING).toDouble()}\n"
            ++i
        }

        binding.tvRanking.text = rankedUsers
    }

    /**
     * Ulozenie poradia kvoli rotacii zariadenia
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("ranks", binding.tvRanking.text.toString())
    }

    /**
     * Nacitanie poradia a vypisanie po opatovnom vytvoreni pohladu, pri rotacii
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            binding.tvRanking.text = "${savedInstanceState.getString("ranks")}"
    }
}