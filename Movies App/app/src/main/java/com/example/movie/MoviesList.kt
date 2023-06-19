package com.example.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class MoviesList(
    private val MoviesList:List<Result>,
    private val activity: FragmentActivity,
    private val clickListener:(Result) -> Unit
    ): RecyclerView.Adapter<MyHorizontalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHorizontalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val list = layoutInflater.inflate(R.layout.horizontalrecview,parent,false)
        return MyHorizontalViewHolder(list,activity)
    }

    override fun onBindViewHolder(holder: MyHorizontalViewHolder, position: Int) {
        var item = MoviesList[position]
        holder.blind(item,clickListener)
    }

    override fun getItemCount(): Int {
        return MoviesList.size
    }
}
class MyHorizontalViewHolder(val view: View,var activity: FragmentActivity):RecyclerView.ViewHolder(view){
    val img = view.findViewById<ImageView>(R.id.imgM)
    val Mname = view.findViewById<TextView>(R.id.tvM)
    fun blind(res : Result,clickListener:(Result) -> Unit){
        Mname.text = res.title
        var imageUrl = "https://image.tmdb.org/t/p/original"+res.poster_path
//        Glide.with(activity)
//            .load(imageUrl)
//            .into(img)
        Picasso.get().load(imageUrl).resize(500,700).into(img)
        view.setOnClickListener{
            val bundle = bundleOf(
                "movie_title" to res.title,
                "movie_poster" to res.poster_path,
                "movie_genres" to res.genre_ids as ArrayList<Int>?,
                "movie_rating" to res.vote_average,
                "movie_about" to res.overview
            )
            it.findNavController().navigate(R.id.action_navigation_home_to_movieInfo,bundle)
//            clickListener(res)
        }
    }
}
