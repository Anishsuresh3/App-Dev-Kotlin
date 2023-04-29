package com.example.opps

import android.util.Log

class characterName(var name : String,rating : Int){
//    lateinit var Name:String
    //var Name=""
//    var char = name
    var rate=0
    var ani = anime()
    init {
//        Name=name
        rate=rating
        ani.gg=190
        ani.animeName()
    }
    fun display(){
        Log.i("anime",name)
        Log.i("anime","Ratings : $rate")
    }
}