package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(
    private val anime:List<Anime>,
    private val clickListener:(Anime) -> Unit
    ) : RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var ani = anime[position]
        holder.blind(ani,clickListener)
    }

    override fun getItemCount(): Int {
        return anime.size
    }
}
class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){
    val myTextView = view.findViewById<TextView>(R.id.tvname)
    fun blind(ani : Anime,clickListener:(Anime) -> Unit){
        myTextView.text = "${ani.name} : ${ani.character}"
        view.setOnClickListener {
            Toast.makeText(
                view.context,
                "Selected Anime is : ${ani.name}",
                Toast.LENGTH_LONG
            ).show()
            clickListener(ani)
        }
    }
}