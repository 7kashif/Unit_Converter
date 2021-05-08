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
import com.example.unitconverter.databinding.FragmentWeightConverterBinding


class WeightConverterFragment : Fragment(),AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentWeightConverterBinding
    private var arrayAdapter: ArrayAdapter<String>?=null
    private val weightUnits= arrayOf("mg","ounce","gram","pound","kg","tonne")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_weight_converter,container,false)
        arrayAdapter= this.context?.let { ArrayAdapter(it,R.layout.support_simple_spinner_dropdown_item,weightUnits) }
        binding.fromSpinner.adapter=arrayAdapter
        binding.toSpinner.adapter=arrayAdapter
        binding.fromSpinner.onItemSelectedListener=this
        binding.toSpinner.onItemSelectedListener=this
        textChangedListener()
        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        milliGram(binding.baseValue.text.toString())
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this.context,"Please select a unit", Toast.LENGTH_LONG).show()
    }

    private fun textChangedListener(){
        binding.baseValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                milliGram(s.toString())
                if(s.toString().isEmpty())
                    binding.convertedValue.text="0.0"
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun milliGram (value:String){
        var mg: Float
        if(value.isNotEmpty()){
            mg=value.toFloat()
            when(binding.fromSpinner.selectedItem.toString()){
                "ounce"-> mg=(mg*28349.5).toFloat()
                "gram"-> mg*=1000
                "pound"-> mg*=453592
                "kg"-> mg=(mg*1000)*1000
                "tonne"-> mg=((mg*1000)*1000)*1000
            }
            convertedValue(mg)
        }

    }
    private fun convertedValue(number:Float){
        var convertedValue=number
        when(binding.toSpinner.selectedItem.toString()){
            "ounce"-> convertedValue=(convertedValue/28349.5).toFloat()
            "gram"-> convertedValue/=1000
            "pound"-> convertedValue/=453592
            "kg"-> convertedValue=(convertedValue/1000)/100
            "tonne"-> convertedValue=((convertedValue/1000)/1000)/1000
        }
        binding.convertedValue.text=convertedValue.toString()
    }
}