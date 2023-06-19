package com.example.movie.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.Movies
import com.example.movie.MoviesGet
import com.example.movie.MoviesList
import com.example.movie.RetrofitInstance
import com.example.movie.databinding.FragmentHomeBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //starts
        var horizrecNowPlaying = binding.rc1
        var horizrecPopular = binding.rc2
        var horizrecTopRated = binding.rc3
        horizrecNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        horizrecPopular.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        horizrecTopRated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        if(homeViewModel.successCall.value==0){
            val retService = RetrofitInstance.getRetrofitInstance().create(MoviesGet::class.java)
            val responseLiveData : LiveData<Response<Movies>> = liveData {
                val response = retService.getNowPlayingMovies("en-US",1)
                emit(response)
            }
            responseLiveData.observe(viewLifecycleOwner, Observer {
                var data = it.body()!!;
                if(data!=null){
                    horizrecNowPlaying.adapter = MoviesList(data!!.results,requireActivity()){ selectedItem: com.example.movie.Result ->
                        MovieClicked(selectedItem)
                    }
                    homeViewModel.responseNPM(data)
                }
            })
            val responseLiveDataPopular : LiveData<Response<Movies>> = liveData {
                val response = retService.getPopularMovies("en-US",1)
                emit(response)
            }
            responseLiveDataPopular.observe(viewLifecycleOwner, Observer {
                var data = it.body();
                if(data!=null){
                    horizrecPopular.adapter = MoviesList(data!!.results,requireActivity()){ selectedItem: com.example.movie.Result ->
                        MovieClicked(selectedItem)
                    }
                    homeViewModel.responsePM(data)
                }
            })
            val responseTopRated: LiveData<Response<Movies>> = liveData {
                val response = retService.getTopRatedMovies("en-US",1)
                emit(response)
            }
            responseTopRated.observe(viewLifecycleOwner, Observer {
                var data = it.body();
                if(data!=null){
                    horizrecTopRated.adapter = MoviesList(data!!.results,requireActivity()){ selectedItem: com.example.movie.Result ->
                        MovieClicked(selectedItem)
                    }
                    homeViewModel.responseTRM(data)
                }
            })
            homeViewModel.update()
        }
        else{
            horizrecNowPlaying.adapter = MoviesList(homeViewModel.nowPlayingMovies.value!!.results,requireActivity()){ selectedItem: com.example.movie.Result ->
                MovieClicked(selectedItem)
            }
            horizrecPopular.adapter = MoviesList(homeViewModel.popularMovies.value!!.results,requireActivity()){ selectedItem: com.example.movie.Result ->
                MovieClicked(selectedItem)
            }
            horizrecTopRated.adapter = MoviesList(homeViewModel.topRatedMovies.value!!.results,requireActivity()){ selectedItem: com.example.movie.Result ->
                MovieClicked(selectedItem)
            }
        }

        //end
        return root
    }
    fun MovieClicked(res: com.example.movie.Result){
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}