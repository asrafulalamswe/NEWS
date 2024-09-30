package com.mdasrafulalam.news.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import com.mdasrafulalam.news.databinding.FragmentSettingsBinding
import com.mdasrafulalam.news.preference.DataPreference
import com.mdasrafulalam.news.utils.Constants
import com.mdasrafulalam.news.viewmodel.NewsViewmodel
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var _binding: FragmentSettingsBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    private var isLinear = true
    private lateinit var layout:String
    private lateinit var preferences: DataPreference
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun chooseImage(isLinear: Boolean) {
        if (isLinear) {
            binding.preferedLayout.setImageResource(com.mdasrafulalam.news.R.drawable.ic_linear_layout)
        } else {
            binding.preferedLayout.setImageResource(com.mdasrafulalam.news.R.drawable.ic_grid_layout)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = DataPreference(requireContext())
        preferences.selectedLayoutFlow.asLiveData().observe(viewLifecycleOwner) {
            if (it=="Linear"){
                Constants.ISLINEARLYOUT.value = true
            }else if(it=="Grid"){
                Constants.ISLINEARLYOUT.value = false
            }
        }
        Constants.ISLINEARLYOUT.observe(viewLifecycleOwner) {
            isLinear = it
            chooseImage(isLinear)
            if (isLinear) {
                layout = "Linear"
            } else {
                layout = "Grid"
            }
        }

        Constants.DARKMODE.observe(viewLifecycleOwner) {
            binding.themeSwitch.isChecked = it
        }
        binding.preferedLayout.setOnClickListener {
            isLinear = !isLinear
            Constants.ISLINEARLYOUT.value = isLinear
            chooseImage(isLinear)
            if (isLinear){
                layout = "Linear"
            }else{
                layout = "Grid"
            }
            lifecycle.coroutineScope.launch {
                preferences.setLayout(layout,
                    requireContext()
                )
            }
        }
        val countryAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.simple_spinner_dropdown_item,
            Constants.countryList
        )
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.settingsFragmentLL.setBackgroundColor(resources.getColor(R.color.darker_gray))
                binding.themeSwitch.setBackgroundColor(resources.getColor(com.mdasrafulalam.news.R.color.nav_bar_start))
                binding.themeSwitch.text = getString(com.mdasrafulalam.news.R.string.disable_dark_mode)
                Constants.DARKMODE.value = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.settingsFragmentLL.setBackgroundColor(resources.getColor(R.color.white))
                binding.themeSwitch.text = getString(com.mdasrafulalam.news.R.string.enable_dark_mode)
                Constants.DARKMODE.value = false
            }
        }


        binding.countrySpinner.adapter = countryAdapter
        preferences.selectedPositionFlow.asLiveData().observe(requireActivity()) {
            binding.countrySpinner.setSelection(it.toInt())
        }
//        binding.countrySpinner.setSelection(24)
        binding.countrySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Constants.COUNTRY.value = Constants.countryCode[position]
                    lifecycle.coroutineScope.launch {
                        preferences.setCountry(
                            Constants.COUNTRY.value.toString(),
                            position.toLong(),
                            requireContext()
                        )
                    }
                    Log.d("country", "Selected Country: ${Constants.COUNTRY.value.toString()}")
                    try {
                        viewModel.refreshData()
                    }catch (e:Exception){
                        Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }
}