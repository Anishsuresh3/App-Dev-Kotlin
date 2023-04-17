package com.example.learn

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var userName = intent.getStringExtra("USER")
        var textView = findViewById<TextView>(R.id.tvoffer)
        val message = "$userName , you have earned a one month subscription!!!"
        textView.text = message
    }
}