package com.example.opps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val ani = anime()
//        ani.animeName()
        var cc = characterName("Light",9)
        cc.display()
        var aa = manga()
        aa.animeName()
    }
}