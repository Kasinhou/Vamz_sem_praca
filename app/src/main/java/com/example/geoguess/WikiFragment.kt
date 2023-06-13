package com.example.geoguess

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.geoguess.activities.CountryInfoViewModel
import com.example.geoguess.databinding.FragmentCountryInfoBinding
import com.example.geoguess.databinding.FragmentWikiBinding
import java.util.concurrent.Executors
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 * Use the [WikiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WikiFragment : Fragment(R.layout.fragment_wiki) {
    private lateinit var binding: FragmentWikiBinding

    private lateinit var sharedViewModel: CountryInfoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWikiBinding.bind(view)

        sharedViewModel = ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
        binding.buttonSearch.setOnClickListener { show() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // requireActivity().setContentView(R.layout.fragment_wiki)

        //requireActivity().setContentView(binding.root)
        //binding.buttonSearch.setOnClickListener { spracuj() }

    }

    private fun show() {
        val text = binding.searchedCountry.text.toString().lowercase().trimEnd()

        if (sharedViewModel.getCountry(text) != null) {
            showImages(text)
            binding.tvCountryName.text = text.uppercase()
            binding.tvInfoAboutCountry.text = sharedViewModel.getCountry(text)?.showInfo()
        }

        //binding.tvInfoAboutCountry.text = sharedViewModel.listOfCountries.value?.get(100)?.showInfo()
    }

    private fun showImages(name: String) {
        val flagUrl = sharedViewModel.getCountry(name)?.flags?.png.toString()
        val coaUrl = sharedViewModel.getCountry(name)?.coatOfArms?.png.toString()

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



    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wiki, container, false)
    }

    companion object {
        *//**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WikiFragment.
         *//*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WikiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}