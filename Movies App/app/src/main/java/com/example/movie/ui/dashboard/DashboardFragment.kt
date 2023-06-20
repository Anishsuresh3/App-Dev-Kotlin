package com.example.movie.ui.dashboard

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.*
import com.example.movie.databinding.FragmentDashboardBinding
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //start
        var searchResult = binding.rcList
        searchResult.layoutManager = LinearLayoutManager(activity)
        var movieName=""
        var input = binding.etMovieName
        var search = binding.search
        search.setOnClickListener {
            if(!TextUtils.isEmpty(input.text.toString())) {
                movieName=input.text.toString()
                getData(movieName,searchResult)
            }
            else{
                Toast.makeText(activity,"please enter a movie name", Toast.LENGTH_LONG).show()
            }
        }
        //end
        return root
    }
    fun getData(movieName:String,searchRes : RecyclerView){
        val retService =
            RetrofitInstance.getRetrofitInstance().create(MoviesGet::class.java)
        val responseLiveData: LiveData<Response<Movies>> = liveData {
            val response = retService.getMovie(movieName, false,"en-US",1)
            emit(response)
        }
        responseLiveData.observe(viewLifecycleOwner, Observer {
            var data = it.body()
            if(data!=null){
                searchRes.adapter = SearchMovies(
                    data!!.results,
                    requireActivity(),
                    R.id.action_navigation_dashboard_to_movieInfo
                ) { selectedItem: Result ->
                    MovieClicked(selectedItem)
                }
            }
        })
    }
    fun MovieClicked(res: com.example.movie.Result){
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}