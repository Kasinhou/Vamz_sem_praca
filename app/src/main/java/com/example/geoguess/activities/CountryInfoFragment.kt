package com.example.geoguess.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geoguess.GuessingFragment
import com.example.geoguess.R
import com.example.geoguess.WikiFragment
import com.example.geoguess.databinding.FragmentCountryInfoBinding
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide

class CountryInfoFragment : Fragment() {

    //private val sharedViewModel: CountryInfoViewModel by activityViewModels()

    /**
     * Lazily initialize our [CountryInfoViewModel].
     */
    private val sharedViewModel: CountryInfoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CountryInfoViewModel::class.java)
    }

   /* companion object {
        fun newInstance() = CountryInfoFragment()
    }*/

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_info, container, false)
    }*/

    private lateinit var binding: FragmentCountryInfoBinding

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedViewModel.getCountriesProperties()
        Log.i("CountryInfoFragment", "CountryInfoFragment created/")
        binding = FragmentCountryInfoBinding.inflate(inflater)
        //val binding = FragmentCountryInfoBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the CountryInfoViewModel
        binding.viewModel = sharedViewModel

        //setHasOptionsMenu(true)
        background()

        binding.buttonGuess.setOnClickListener { goToQuiz(GuessingFragment()) }
        binding.buttonInfo.setOnClickListener { goToInfo(WikiFragment()) }
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CountryInfoFragment", "CountryInfoFragment destroyed!")
    }

    private fun background() {
        Glide.with(this)
            .load("https://e0.pxfuel.com/wallpapers/427/996/desktop-wallpaper-world-map-iphone-x-map-abstract.jpg")
            .into(binding.backgroundMap)
    }

    private fun goToInfo(newFragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainerView, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun goToQuiz(newFragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainerView, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountryInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    /*fun getList(): LiveData<List<Country>> {
        return viewModel.listOfCountries
    }*/
}