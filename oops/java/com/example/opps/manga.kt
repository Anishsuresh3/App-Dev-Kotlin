package com.example.opps

import android.util.Log

class manga : anime() {
    override fun animeName(){
        Log.i("anime","inherit")
    }
}