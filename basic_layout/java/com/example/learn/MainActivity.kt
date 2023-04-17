package com.example.learn

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val greetingTextView = findViewById<TextView>(R.id.HelloTxt)
        val inputField = findViewById<EditText>(R.id.editTextTextPersonName)
        val buttonField = findViewById<Button>(R.id.button)
        val displayText = findViewById<TextView>(R.id.textView2)
        buttonField.setOnClickListener{
            val enteredName = inputField.text.toString()
            if(enteredName==""){
                displayText.text = ""
                // application context and activity context are both instances of context class but
                // the application context is tied to the life cycle of entire application
                // while the activity context is tied to the life cycle of an activity
                Toast.makeText(this@MainActivity,"Please enter your name",Toast.LENGTH_SHORT).show()
            }
            else{
                val message = "Welcome $enteredName"
                displayText.text = message
                displayText.textSize = 24F
                inputField.text.clear()
            }
        }
    }
}