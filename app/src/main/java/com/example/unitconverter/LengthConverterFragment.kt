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
import com.example.unitconverter.databinding.FragmentLengthConverterBinding


class LengthConverterFragment : Fragment(),AdapterView.OnItemSelectedListener {
    private lateinit var binding:FragmentLengthConverterBinding
    private var arrayAdapter: ArrayAdapter<String>?=null
    private val lengthUnits= arrayOf("mm","cm","inch","foot","meter","km","mile")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_length_converter,container,false)
        arrayAdapter= this.context?.let { ArrayAdapter(it,R.layout.support_simple_spinner_dropdown_item,lengthUnits) }
        binding.fromSpinner.adapter=arrayAdapter
        binding.toSpinner.adapter=arrayAdapter
        binding.fromSpinner.onItemSelectedListener=this
        binding.toSpinner.onItemSelectedListener=this
        textChangedListener()
        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        toMilliMeters(binding.baseValue.text.toString())
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this.context,"Please select a unit",Toast.LENGTH_LONG).show()
    }

    private fun textChangedListener(){
        binding.baseValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                toMilliMeters(s.toString())
                if(s.toString().isEmpty())
                    binding.convertedValue.text="0.0"
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun toMilliMeters (value:String){
        var milliMeters: Float
        if(value.isNotEmpty()){
            milliMeters=value.toFloat()
            when(binding.fromSpinner.selectedItem.toString()){
                "cm"->  milliMeters*=10
                "inch"->  milliMeters= ((milliMeters*10)*2.54).toFloat()
                "foot"->  milliMeters= (((milliMeters*10)*2.54)*12).toFloat()
                "meter"-> milliMeters *= 1000
                "km"-> milliMeters *= 1000000
                "mile"->  milliMeters= ((milliMeters*1000000)*1.6).toFloat()
            }
            convertedValue(milliMeters)
        }
    }
    private fun convertedValue(number:Float){
        var convertedValue=number
        when(binding.toSpinner.selectedItem.toString()){
            "cm"->  convertedValue/=10
            "inch"->  convertedValue= ((convertedValue/10)/2.54).toFloat()
            "foot"->  convertedValue= (((convertedValue/10)/2.54)/12).toFloat()
            "meter"->  convertedValue= ((convertedValue/10)/100)
            "km"->  convertedValue= ((convertedValue/10)/100)/1000
            "mile"->  convertedValue= ((((convertedValue/10)/100)/1000)/1.6).toFloat()
        }
        binding.convertedValue.text=convertedValue.toString()
    }
}