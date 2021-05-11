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
import androidx.navigation.fragment.findNavController
import com.example.unitconverter.databinding.FragmentTimeConverterBinding


class TimeConverterFragment : Fragment(),AdapterView.OnItemSelectedListener {
    private lateinit var binding:FragmentTimeConverterBinding
    private var arrayAdapter:ArrayAdapter<String>?=null
    private val timeUnits= arrayOf("Secs","Min","Hrs","Days","Weeks","Months","Years")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_time_converter,container,false )
        arrayAdapter= this.context?.let { ArrayAdapter(it,R.layout.support_simple_spinner_dropdown_item,timeUnits) }
        binding.fromSpinner.adapter=arrayAdapter
        binding.toSpinner.adapter=arrayAdapter
        binding.fromSpinner.onItemSelectedListener=this
        binding.toSpinner.onItemSelectedListener=this
        textChangedListener()

        binding.backButton.setOnClickListener {
            this.findNavController().navigate(TimeConverterFragmentDirections.actionTimeConverterFragmentToHomeFragment())
        }

        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
       toSeconds(binding.baseValue.text.toString())
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this.context,"Select an Option from the List",Toast.LENGTH_LONG).show()
    }

    private fun textChangedListener(){
        binding.baseValue.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)=Unit
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                toSeconds(s.toString())
                if(s.toString().isEmpty())
                    binding.convertedValue.text="0.0"
            }
            override fun afterTextChanged(p0: Editable?)=Unit
        })
    }

    private fun toSeconds (value:String){
        var seconds: Float
        if(value.isNotEmpty()){
            seconds=value.toFloat()
            when(binding.fromSpinner.selectedItem.toString()){
                "Min"-> seconds *= 60
                "Hrs"-> seconds*=3600
                "Days"-> seconds=((seconds*24)*3600)
                "Weeks"-> seconds=(((seconds*7)*24)*3600)
                "Months"-> seconds=(((seconds*30)*24)*3600)
                "Years"-> seconds=(((seconds*365)*24)*3600)
            }
            convertedValue(seconds)
        }
    }
    private fun convertedValue(number:Float){
        var value=number
        when(binding.toSpinner.selectedItem.toString()){
            "Min"-> value/=60
            "Hrs"-> value/=3600
            "Days"-> value=(value/3600)/24
            "Weeks"-> value=(((value/3600)/24)/7)
            "Months"->value=(((value/3600)/24)/30)
            "Years"->value=(((value/3600)/24)/365)
        }
        binding.convertedValue.text=value.toString()
    }

}