package com.example.recyclerview

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val anime = listOf<Anime>(
        Anime("Demon Slayer","Tanjiro"),Anime("AOT","Eren yeager"),Anime("One Piece","Luffy"),Anime("JJK","Gojo"),Anime("Vinland Saga","Thorfinn"),Anime("Naruto","naruto")
    )
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rec = findViewById<RecyclerView>(R.id.recyclerView)
        rec.setBackgroundColor(Color.GRAY)
        rec.layoutManager = LinearLayoutManager(this)
        rec.adapter = ViewAdapter(
            anime,
        ) { selectedItem: Anime ->
            animeclicked(selectedItem)
        }
    }
    private fun animeclicked(ani:Anime){
        Toast.makeText(
            this@MainActivity,
            "Character is : ${ani.character}",
            Toast.LENGTH_LONG
        ).show()
    }
}