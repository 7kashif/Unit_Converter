package com.example.unitconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unitconverter.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentHomeBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        binding.toTimeFragment.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTimeConverterFragment())
        }
        binding.toLengthFragment.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLengthConverterFragment())
        }
        binding.toTempFragment.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTemperatureConverterFragment())
        }
        binding.toWeightFragment.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWeightConverterFragment())
        }
        return binding.root
    }
}