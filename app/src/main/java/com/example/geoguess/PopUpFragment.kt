package com.example.geoguess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.geoguess.activities.CountryInfoFragment
import com.example.geoguess.databinding.FragmentPopUpBinding


/**
 * A simple [Fragment] subclass.
 * Use the [PopUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopUpFragment : DialogFragment() {
    private lateinit var binding: FragmentPopUpBinding

    private var minutes: Long = 0
    private var seconds: Long = 0
    private var exit: Boolean = false

    companion object {
        fun instance(min: Long, sec: Long, exit: Boolean) : PopUpFragment {
            val newFragment = PopUpFragment()
            newFragment.minutes = min
            newFragment.seconds = sec
            newFragment.exit = exit
            return newFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopUpBinding.bind(view)

        binding.btnOK.setOnClickListener { saveScore() }

    }

    private fun saveScore() {
        if (binding.nameToSave.text == null)
            return


        dismiss()
    }
}