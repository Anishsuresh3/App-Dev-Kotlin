package com.example.weatherapp

import android.content.Context
import android.net.Uri
import android.net.Uri.parse
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class HorizontalViewAdapter(private val hour: List<Hour>,private val currentHour : Int,private val packageName:String,val is_day:Int):RecyclerView.Adapter<MyHorizontalViewHolder>(){
    private var count=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHorizontalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val list = layoutInflater.inflate(R.layout.horizontal_lists,parent,false)
        return MyHorizontalViewHolder(list,packageName,parent.context)
    }

    override fun onBindViewHolder(holder: MyHorizontalViewHolder, position: Int) {
        var listpart = hour[position]
        holder.blind(listpart,is_day)
//        Log.i("posiiii",position.toString())
    }
    override fun getItemCount(): Int {
        return 24
    }

}
class MyHorizontalViewHolder(view : View,private val packageName:String,private val context: Context):RecyclerView.ViewHolder(view){
    val textView = view.findViewById<TextView>(R.id.tv01)
    val imageV = view.findViewById<ImageView>(R.id.img01)
    val textView2 = view.findViewById<TextView>(R.id.tv02)
    val cd = view.findViewById<CardView>(R.id.cd1)
    fun blind(hour:Hour,is_day:Int){
        textView.text = if(hour.time.subSequence(11, 13).toString().toInt()<12) "${hour.time.subSequence(11, 13)}:00 AM" else "${hour.time.subSequence(11, 13)}:00 PM"
        textView2.text = "${hour.temp_c} C"
        if(is_day==0){
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            textView2.setTextColor(ContextCompat.getColor(context, R.color.white))
            cd.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.system_accent2_800))
        }
        select_img(hour,imageV)
    }
    fun select_img(hour : Hour,imgg : ImageView){
        if("rain" in hour.condition.text){
            var urI = Uri.parse("android.resource://" + packageName + "/" + R.drawable.raining)
            imgg.setImageURI(urI)
        }
        else if("clear" in hour.condition.text.lowercase()){
            if(hour.is_day==0) {
                var urI =
                    Uri.parse("android.resource://" + packageName + "/" + R.drawable.clear_night)
                imgg.setImageURI(urI)
            }
            else{
                var urI =
                    Uri.parse("android.resource://" + packageName + "/" + R.drawable.clear_day)
                imgg.setImageURI(urI)
            }
        }
        else if("thunder" in hour.condition.text.lowercase()){
            var urI =
                Uri.parse("android.resource://" + packageName + "/" + R.drawable.raining_lightning)
            imgg.setImageURI(urI)
        }
        else if("sunny" in hour.condition.text.lowercase()){
            var urI = Uri.parse("android.resource://" + packageName + "/" + R.drawable.clear_day)
            imgg.setImageURI(urI)
        }
        else if("partly" in hour.condition.text.lowercase()){
            if(hour.is_day==1) {
                var urI =
                    Uri.parse("android.resource://" + packageName + "/" + R.drawable.day_sky)
                imgg.setImageURI(urI)
            }
            else {
                var urI =
                    Uri.parse("android.resource://" + packageName + "/" + R.drawable.night_sky)
                imgg.setImageURI(urI)
            }
        }
    }
}