package com.example.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movie.databinding.FragmentHomeBinding
import com.example.movie.databinding.FragmentMovieInfoBinding
import com.squareup.picasso.Picasso

class MovieInfo : Fragment() {
    private lateinit var binding: FragmentMovieInfoBinding
//    val numbersMap = mutableMapOf<Int, String>().apply { this[28] = "Action"; this[12] = "2" }
    val numbersMap2 = mapOf(28 to "Action", 12 to "Adventure", 16 to "Animation", 35 to "Comedy",80 to "Crime",99 to "Documentary",18 to "Drama",10751 to "Family",14 to "Fantasy",36 to "History",27 to "Horror",10402 to "Music",9648 to "Music",14 to "Mystery",10749 to "Romance",878 to "Science Fiction",10770 to "TV Movie",53 to "Thriller",10752 to "War",37 to "Western")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieInfoBinding.inflate(inflater,container,false)
        val input = requireArguments().getString("movie_title")
        val imgpath = requireArguments().getString("movie_poster")
        val genres = requireArguments().getIntegerArrayList("movie_genres")
        val rating = requireArguments().getDouble("movie_rating")
        val des = requireArguments().getString("movie_about")
        binding.tvMovieName.text = input.toString()
        var genres_names=""
        var body = genres!!.iterator()
        while (body.hasNext()){
            var e = body.next()
            genres_names+=numbersMap2[e]+"/"
        }
        Log.i("RRR",rating.toString())
        binding.tvGenres.text=genres_names
        binding.tvRating.text = rating.toString()+"/10.0"
//        binding.tvDescription.text = des
        binding.tvDescription.text = des
        binding.tvDescription.maxLines = 4
        binding.btnShow.setOnClickListener {
            if(binding.btnShow.text=="more..."){
                binding.tvDescription.maxLines = Int.MAX_VALUE
                binding.btnShow.text="less..."
            }
            else{
                binding.tvDescription.maxLines = 4
                binding.btnShow.text="more..."
            }
        }
        Picasso.get().load("https://image.tmdb.org/t/p/original"+imgpath).into(binding.imgMovie)
        return binding.root
    }

}