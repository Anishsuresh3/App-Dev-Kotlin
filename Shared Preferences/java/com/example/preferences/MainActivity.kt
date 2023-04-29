package com.example.preferences

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var UName:EditText
    private lateinit var UAge:EditText
    private lateinit var sf:SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UName = findViewById(R.id.Name)
        UAge = findViewById(R.id.age)
        //Mode private makes the shared preferences file private and accessible within our app
        //Mode append this will append with the already existing preferences
        sf = getSharedPreferences("Users Data", MODE_PRIVATE)
        editor = sf.edit()
    }

    override fun onPause() {
        super.onPause()
        var name = UName.text.toString()
        var age = UAge.text.toString().toInt()
        editor.apply {
            putString("User_Name",name)
            putInt("User_Age",age)
            commit() // if we don't commit then the values wont be saved
        }
    }

    override fun onResume() {
        super.onResume()
        var name = sf.getString("User_Name","")
        var age = sf.getInt("User_Age",0)
        UName.setText(name.toString())
        if(age!=0){
            UAge.setText(age.toString())
        }
    }

}