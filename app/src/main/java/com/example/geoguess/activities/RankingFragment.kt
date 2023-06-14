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

class RankingFragment : Fragment() {
    private lateinit var binding: FragmentRankingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRankingBinding.bind(view)

        binding.btnAvgGuessed.setOnClickListener { rankUsers(true) }
        binding.btnMaxCount.setOnClickListener { rankUsers(false) }
    }

    private fun rankUsers(byAvg: Boolean) {
        var data = Database(requireContext()).load()
        var rankings: MutableMap<Double, String> = mutableMapOf()
        var sortRanking: MutableMap<Double, String> = mutableMapOf()
        var rankedUsers = ""
        var i = 1

        if (byAvg) {
            data.forEach { it ->
                if (it.count == 0)
                    rankings[1000.0] = "${it.user}, time: ${it.min}min and ${it.sec}sec, guessed: ${it.count}"
                else
                    rankings[((((it.sec / 60.0) * 100 + it.min) / it.count) / 100) * 60] =
                        "${it.user}, time: ${it.min}min and ${it.sec}sec, guessed: ${it.count}"
            }
            sortRanking = rankings.toSortedMap()
        } else {
            data.forEach { it ->
                rankings[it.count.toDouble()] = "${it.user}, time: ${it.min}min and ${it.sec}sec, guessed: ${it.count}"
            }
            sortRanking = rankings.toSortedMap(compareByDescending { it })
        }

        for ((key, value) in sortRanking) {
            rankedUsers += "$i. $value, ratio: ${BigDecimal(key).setScale(2, RoundingMode.CEILING).toDouble()}\n"
            ++i
        }

        binding.tvRanking.text = rankedUsers
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("ranks", binding.tvRanking.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            binding.tvRanking.text = "${savedInstanceState.getString("ranks")}"
    }
}