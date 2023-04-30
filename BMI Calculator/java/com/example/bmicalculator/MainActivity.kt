package com.example.bmicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightText = findViewById<EditText>(R.id.etweight)
        val heightText = findViewById<EditText>(R.id.etheight)

        val calcbutton = findViewById<Button>(R.id.bimcalculate)

        calcbutton.setOnClickListener {
            var weight = weightText.text.toString()
            var height = heightText.text.toString()
            if(validateInput(weight,height)) {
                var bodyMass =
                    weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))

                var bmiResult = String.format("%.2f", bodyMass).toFloat()
                display(bmiResult)
            }
        }
    }

    private fun validateInput(weight:String?,height:String?):Boolean{
        return  when{
            weight.isNullOrEmpty()->{
                Toast.makeText(this,"Weight is empty",Toast.LENGTH_LONG).show()
                return false
            }
            height.isNullOrEmpty()->{
                Toast.makeText(this,"Height is empty",Toast.LENGTH_LONG).show()
                return false
            }
            else -> return true
        }
    }

    private fun display(res:Float){
        val resultIndex = findViewById<TextView>(R.id.tvindex)
        val resultDesc = findViewById<TextView>(R.id.tvresult)
        val info = findViewById<TextView>(R.id.tvinfo)

        resultIndex.text = res.toString()
        var resText = ""
        var color = 0

        when{
            res<18.50 -> {
                resText = "UnderWeight"
                color = R.color.underweight
            }
            res in 18.50..24.99 -> {
                resText = "Healthy"
                color = R.color.normal
            }
            res in 25.00..29.99 -> {
                resText = "OverWeight"
                color = R.color.over_weight
            }
            res>29.99 -> {
                resText = "Obese"
                color = R.color.obese
            }
        }
        resultDesc.setTextColor(ContextCompat.getColor(this,color))
        resultDesc.text = resText
    }
}