package com.example.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    var count = 0
    private lateinit var viewModel : MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val countbutton = findViewById<Button>(R.id.btcount)
        val textcount = findViewById<TextView>(R.id.tvcount)
//        textcount.text = viewModel.count.toString()
        viewModel.count.observe(this, Observer {
            textcount.text = it.toString()
        })
        countbutton.setOnClickListener {
//            count++
//            textcount.text = count.toString()
            viewModel.updateCount()
//            textcount.text = viewModel.count.toString()
        }
    }
}