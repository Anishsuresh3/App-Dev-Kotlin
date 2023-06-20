package com.example.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class SearchMovies (
    private val MoviesList:List<Result>,
    private val activity: FragmentActivity,
    private val actionId:Int,
    private val clickListener:(Result) -> Unit,
    ): RecyclerView.Adapter<MySearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val list = layoutInflater.inflate(R.layout.movie_search_result,parent,false)
        return MySearchViewHolder(list,activity)
    }

    override fun onBindViewHolder(holder: MySearchViewHolder, position: Int) {
        var item = MoviesList[position]
        holder.blind(item,clickListener,actionId)
    }

    override fun getItemCount(): Int {
        return MoviesList.size
    }
}
class MySearchViewHolder(val view: View, var activity: FragmentActivity): RecyclerView.ViewHolder(view){
    val img = view.findViewById<ImageView>(R.id.imgMovieSearch)
    val Mname = view.findViewById<TextView>(R.id.tvMovieSearch)
    val rate = view.findViewById<TextView>(R.id.tvratingaverage)
    fun blind(res : Result,clickListener:(Result) -> Unit,actionId:Int){
        Mname.text = res.title
        var imageUrl = "https://image.tmdb.org/t/p/original"+res.poster_path
        rate.text = res.vote_average.toString()+"/10.0"
        Picasso.get().load(imageUrl).resize(0,700).into(img)
        view.setOnClickListener{
            val resMovie =  Gson().toJson(res)
            val bundle = bundleOf(
                "movie_title" to res.title,
                "movie_poster" to res.poster_path,
                "movie_genres" to res.genre_ids as ArrayList<Int>?,
                "movie_rating" to res.vote_average,
                "movie_about" to res.overview,
                "movie" to  resMovie
            )
            it.findNavController().navigate(actionId,bundle)
        }
    }
}
