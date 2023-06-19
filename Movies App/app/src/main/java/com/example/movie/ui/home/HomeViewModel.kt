package com.example.movie.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.Movies

class HomeViewModel : ViewModel() {

    private var getResponse = MutableLiveData<Int>().apply {
        value = 0
    }
    var successCall: LiveData<Int> = getResponse
    var nowPlayingMovies = MutableLiveData<Movies>()
    var popularMovies = MutableLiveData<Movies>()
    var topRatedMovies = MutableLiveData<Movies>()
    fun responsePM(res : Movies){
        popularMovies.value = res
    }
    fun responseTRM(res : Movies){
        topRatedMovies.value = res
    }
    fun responseNPM(res : Movies){
        nowPlayingMovies.value = res
    }
    fun update(){
        getResponse.apply {
            value=1
        }
    }
}