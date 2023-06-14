package com.example.geoguess.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.geoguess.data.Database
import com.example.geoguess.R
import com.example.geoguess.databinding.FragmentPopUpBinding


/**
 * Fragment na ulozenie skore podla zadaneho mena
 */
class PopUpFragment : DialogFragment() {
    private lateinit var binding: FragmentPopUpBinding

    private var minutes: Long = 0
    private var seconds: Long = 0
    private var count: Int = 0

    /**
     * Vytvorenie a vratenie instancie fragmentu s parametrami minuty, sekundy a poctu uhadnutych krajin konkretnej hry
     */
    companion object {
        fun instance(min: Long, sec: Long, count: Int) : PopUpFragment {
            val newFragment = PopUpFragment()
            newFragment.minutes = min
            newFragment.seconds = sec
            newFragment.count = count
            return newFragment
        }
    }

    /**
     * vytvorenie pohladu
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_up, container, false)
    }

    /**
     * nastavenie reakcie na tlacidlo OK
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopUpBinding.bind(view)

        binding.btnOK.setOnClickListener { saveScore() }

    }

    /**
     * Ak je zadane meno, do lokalnej databazy sa vlozia atributy konkretneho skore
     * V pripade mena ktore uz je v databaze ulozene sa PopupFragment ukaze znova
     * po uspesnom insertnuti fragment zmizne
     */
    private fun saveScore() {
        if (binding.nameToSave.text == null || binding.nameToSave.text.equals(""))
            return

        if (Database(requireContext()).insertN(binding.nameToSave.text.toString(), minutes.toInt(), seconds.toInt(), count).toInt() == -1) {
            instance(minutes, seconds, count).show((activity as AppCompatActivity).supportFragmentManager, "Pop Up Window")
        }
        dismiss()
    }

    /**
     * Ulozenie stavu fragmentu
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("min", minutes)
        outState.putLong("sec", seconds)
        outState.putInt("count", count)
    }

    /**
     * Nacitanie stavu po otoceni zariadenia
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            savedInstanceState.getLong("min", minutes)
            savedInstanceState.getLong("sec", seconds)
            savedInstanceState.getInt("count", count)
            instance(minutes, seconds, count).show((activity as AppCompatActivity).supportFragmentManager, "Pop Up Window")
        }
    }
}