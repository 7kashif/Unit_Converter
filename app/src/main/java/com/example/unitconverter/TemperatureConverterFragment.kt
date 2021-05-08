package com.example.unitconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.unitconverter.databinding.FragmentTemperatureConverterBinding


class TemperatureConverterFragment : Fragment(),AdapterView.OnItemSelectedListener {
    private  lateinit var binding:FragmentTemperatureConverterBinding
    private  var arrayAdapter: ArrayAdapter<String>?=null
    private val tempUnits= arrayOf("Kelvin","Fahrenheit","Celsius")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_temperature_converter,container,false)
        arrayAdapter= this.context?.let { ArrayAdapter(it,R.layout.support_simple_spinner_dropdown_item,tempUnits) }
        binding.fromSpinner.adapter=arrayAdapter
        binding.toSpinner.adapter=arrayAdapter
        binding.fromSpinner.onItemSelectedListener=this
        binding.toSpinner.onItemSelectedListener=this
        textChangedListener()
        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        toCelsius(binding.baseValue.text.toString())
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this.context,"Please select a unit", Toast.LENGTH_LONG).show()
    }

    private fun textChangedListener(){
        binding.baseValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                toCelsius(s.toString())
                if(s.toString().isEmpty())
                    binding.convertedValue.text="0.0"
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun toCelsius (value:String){
        var celsius: Float
        if(value.isNotEmpty()){
            celsius=value.toFloat()
            when(binding.fromSpinner.selectedItem.toString()){
                "Kelvin"-> celsius-=273.15.toFloat()
                "Fahrenheit"->celsius=(celsius-32)*(5/9)
            }
            convertedValue(celsius)
        }
    }
    private fun convertedValue(number:Float){
        var convertedValue=number
        when(binding.toSpinner.selectedItem.toString()){
            "Kelvin"-> convertedValue+=273.15.toFloat()
            "Fahrenheit"->convertedValue=(convertedValue*9/5)+32
        }
        binding.convertedValue.text=convertedValue.toString()
    }

}