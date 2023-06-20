package com.example.movie.ui.notifications

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.*
import com.example.movie.databinding.FragmentNotificationsBinding
import com.google.gson.Gson
import retrofit2.Response

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //start
        getlist()
        //end
        return root
    }
    fun MovieClicked(res: com.example.movie.Result){
    }
    fun getlist(){
        sf = requireActivity().getSharedPreferences("User_data", Context.MODE_PRIVATE)
        editor = sf.edit()
        val retService =
            RetrofitInstance.getRetrofitInstance().create(MoviesGet::class.java)
        val recW = binding.rcwatch
        recW.layoutManager = LinearLayoutManager(activity)
        var array :Array<com.example.movie.Result>
        val jsonString = sf.getString("User_Movies", null)
        if(jsonString!=null){
            array = Gson().fromJson(jsonString, Array<com.example.movie.Result>::class.java)
            recW.adapter = SearchMovies(
                array.toList(),
                requireActivity(),
                R.id.action_navigation_notifications_to_movieInfo
            ) { selectedItem: Result ->
                MovieClicked(selectedItem)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        getlist()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}