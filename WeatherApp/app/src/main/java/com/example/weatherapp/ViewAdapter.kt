package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(
    private val forecastList:List<Forecastday>,
    private val curr:Current,
    private val clickListener:(Forecastday)->Unit
):RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var day = forecastList[position]
        holder.blind(day,curr,clickListener)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }


}
class MyViewHolder(val view : View):RecyclerView.ViewHolder(view){
    val Textday = view.findViewById<TextView>(R.id.tvday)
    val imgsts = view.findViewById<ImageView>(R.id.imgstatus)
    val state = view.findViewById<TextView>(R.id.tvstate)
    val avg = view.findViewById<TextView>(R.id.tvavgtemp)
    val card = view.findViewById<CardView>(R.id.card)
    fun blind(forecast:Forecastday,curr:Current,clickListener:(Forecastday) -> Unit){
        if(curr.is_day==0){
            card.setCardBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.system_accent2_800))
            state.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            avg.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
        }
        state.text = forecast.day.condition.text
        avg.text = "${forecast.day.maxtemp_c.toInt()}/${forecast.day.mintemp_c.toInt()}"
    }
}