package com.example.movie.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
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
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.*
import com.example.movie.databinding.FragmentHomeBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var isFirstLaunch = true
    private var isComingFromFragment = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //starts
//        var horizrecNowPlaying = binding.rc1
//        var horizrecPopular = binding.rc2
//        var horizrecTopRated = binding.rc3
//        if(isInternetConnected(requireContext())) {
//            request(homeViewModel,horizrecNowPlaying,horizrecPopular,horizrecTopRated)
//        }
//        else{
//            requestInternetConnection(requireContext())
//        }
        checkRequirements()
        //end
        return root
    }
    fun checkRequirements(){
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        var horizrecNowPlaying = binding.rc1
        var horizrecPopular = binding.rc2
        var horizrecTopRated = binding.rc3
        if(isInternetConnected(requireContext())) {
            request(homeViewModel,horizrecNowPlaying,horizrecPopular,horizrecTopRated)
        }
        else{
            requestInternetConnection(requireContext())
        }
    }
    fun request(homeViewModel:HomeViewModel,horizrecNowPlaying:RecyclerView,horizrecPopular:RecyclerView,horizrecTopRated:RecyclerView){
        horizrecNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        horizrecPopular.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        horizrecTopRated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        if (homeViewModel.successCall.value == 0) {
            val retService =
                RetrofitInstance.getRetrofitInstance().create(MoviesGet::class.java)
            val responseLiveData: LiveData<Response<Movies>> = liveData {
                val response = retService.getNowPlayingMovies("en-US", 1)
                emit(response)
            }
            responseLiveData.observe(viewLifecycleOwner, Observer {
                var data = it.body()!!;
                if (data != null) {
                    horizrecNowPlaying.adapter = MoviesList(
                        data!!.results,
                        requireActivity()
                    ) { selectedItem: com.example.movie.Result ->
                        MovieClicked(selectedItem)
                    }
                    homeViewModel.responseNPM(data)
                }
            })
            val responseLiveDataPopular: LiveData<Response<Movies>> = liveData {
                val response = retService.getPopularMovies("en-US", 1)
                emit(response)
            }
            responseLiveDataPopular.observe(viewLifecycleOwner, Observer {
                var data = it.body();
                if (data != null) {
                    horizrecPopular.adapter = MoviesList(
                        data!!.results,
                        requireActivity()
                    ) { selectedItem: com.example.movie.Result ->
                        MovieClicked(selectedItem)
                    }
                    homeViewModel.responsePM(data)
                }
            })
            val responseTopRated: LiveData<Response<Movies>> = liveData {
                val response = retService.getTopRatedMovies("en-US", 1)
                emit(response)
            }
            responseTopRated.observe(viewLifecycleOwner, Observer {
                var data = it.body();
                if (data != null) {
                    horizrecTopRated.adapter = MoviesList(
                        data!!.results,
                        requireActivity()
                    ) { selectedItem: com.example.movie.Result ->
                        MovieClicked(selectedItem)
                    }
                    homeViewModel.responseTRM(data)
                }
            })
            homeViewModel.update()
        } else {
            horizrecNowPlaying.adapter = MoviesList(
                homeViewModel.nowPlayingMovies.value!!.results,
                requireActivity()
            ) { selectedItem: com.example.movie.Result ->
                MovieClicked(selectedItem)
            }
            horizrecPopular.adapter = MoviesList(
                homeViewModel.popularMovies.value!!.results,
                requireActivity()
            ) { selectedItem: com.example.movie.Result ->
                MovieClicked(selectedItem)
            }
            horizrecTopRated.adapter = MoviesList(
                homeViewModel.topRatedMovies.value!!.results,
                requireActivity()
            ) { selectedItem: com.example.movie.Result ->
                MovieClicked(selectedItem)
            }
        }
    }
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    // Function to request internet connection
    fun requestInternetConnection(context: Context) {
        val intent = Intent(Settings.ACTION_SETTINGS)
        context.startActivity(intent)
    }
    fun MovieClicked(res: com.example.movie.Result){
    }

    override fun onResume() {
        super.onResume()
        Log.i("RRR","the screen is resumed")
        if(!isFirstLaunch ){
            if(!isComingFromFragment){
                Log.i("RRR","check is executed")
                checkRequirements()
            }
            else{
                isComingFromFragment=false
            }
        }
        else{
            isFirstLaunch=false
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("RRR","the screen is paused")
        (requireActivity() as? HomeFragment)?.isComingFromFragment = true
    }

    override fun onStop() {
        super.onStop()
        Log.i("RRR","the screen is stopped")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}