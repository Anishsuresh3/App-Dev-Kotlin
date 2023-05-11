package com.example.weatherapp

import android.annotation.SuppressLint
import android.net.Uri
import android.net.Uri.parse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var current: Current
    private lateinit var location: Location
    private lateinit var forecastList: ListIterator<Forecastday>
    private lateinit var videoView:VideoView
    private lateinit var loc:TextView
    private lateinit var temp:TextView
    private lateinit var date:TextView
    private lateinit var status:TextView
    private lateinit var frame:FrameLayout
    private lateinit var rec:RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        videoView = findViewById<VideoView>(R.id.videoView)
        loc = findViewById<TextView>(R.id.tvlocation)
        temp = findViewById<TextView>(R.id.tvtemp)
        date = findViewById<TextView>(R.id.tvdate)
        status = findViewById<TextView>(R.id.tvstatus)
        frame = findViewById(R.id.FrameVideo)
        val cons = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        rec = findViewById<RecyclerView>(R.id.rcView)
        rec.layoutManager = LinearLayoutManager(this)

        videoView.setOnPreparedListener{
            mediaPlayer -> mediaPlayer.isLooping = true
        }
        val retService = RetrofitInstance.getRetrofitInstance().create(currentW::class.java)
        val responseLiveData : LiveData<Response<Forecast>> = liveData {
            val response = retService.getCurrentWeather()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val cur = it.body();
            if(cur!=null){
                current= cur.current
                location = cur.location
                forecastList = cur.forecast.forecastday.listIterator()
                rec.adapter = ViewAdapter(
                    cur.forecast.forecastday,
                ){ selectedItem: Forecastday ->
                    forecastclicked(selectedItem)
                }
                setAll(current,location,forecastList)
            }
        })
    }
    private fun forecastclicked(forecast : Forecastday){

    }
    private fun setAll(current: Current, location: Location, forecastList : ListIterator<Forecastday>){
//        var imgg = findViewById<ImageView>(R.id.im1)
//         var urI = parse("android.resource://"+packageName+"/"+R.drawable.cloudy_dn)
//        imgg.setImageURI(urI)
        val deg = findViewById<TextView>(R.id.tvdegrees)
        val back = findViewById<ConstraintLayout>(R.id.weather)
        var imgList = listOf<ImageView>(findViewById(R.id.im1),findViewById(R.id.im2),findViewById(R.id.im3),findViewById(R.id.im4),findViewById(R.id.im5),findViewById(R.id.im6),findViewById(R.id.im7),findViewById(R.id.im8),findViewById(R.id.im9),findViewById(R.id.im10),findViewById(R.id.im11),findViewById(R.id.im12),findViewById(R.id.im13),findViewById(R.id.im14),findViewById(R.id.im15),findViewById(R.id.im16),findViewById(R.id.im17),findViewById(R.id.im18),findViewById(R.id.im19),findViewById(R.id.im20),findViewById(R.id.im21),findViewById(R.id.im22),findViewById(R.id.im23),findViewById(R.id.im24))

        var listofIds = listOf<TextView>(findViewById(R.id.tvhour1),findViewById(R.id.tvhour2),findViewById(R.id.tvhour3),findViewById(R.id.tvhour4),findViewById(R.id.tvhour5),findViewById(R.id.tvhour6),findViewById(R.id.tvhour7),findViewById(R.id.tvhour8),findViewById(R.id.tvhour9),findViewById(R.id.tvhour10),findViewById(R.id.tvhour11),findViewById(R.id.tvhour12),findViewById(R.id.tvhour13),findViewById(R.id.tvhour14),findViewById(R.id.tvhour15),findViewById(R.id.tvhour16),findViewById(R.id.tvhour17),findViewById(R.id.tvhour18),findViewById(R.id.tvhour19),findViewById(R.id.tvhour20),findViewById(R.id.tvhour21),findViewById(R.id.tvhour22),findViewById(R.id.tvhour23),findViewById(R.id.tvhour24))
        var listofIdsforTemp = listOf<TextView>(findViewById(R.id.tvhourdegree1),findViewById(R.id.tvhourdegree2),findViewById(R.id.tvhourdegree3),findViewById(R.id.tvhourdegree4),findViewById(R.id.tvhourdegree5),findViewById(R.id.tvhourdegree6),findViewById(R.id.tvhourdegree7),findViewById(R.id.tvhourdegree8),findViewById(R.id.tvhourdegree9),findViewById(R.id.tvhourdegree10),findViewById(R.id.tvhourdegree11),findViewById(R.id.tvhourdegree12),findViewById(R.id.tvhourdegree13),findViewById(R.id.tvhourdegree14),findViewById(R.id.tvhourdegree15),findViewById(R.id.tvhourdegree16),findViewById(R.id.tvhourdegree17),findViewById(R.id.tvhourdegree18),findViewById(R.id.tvhourdegree19),findViewById(R.id.tvhourdegree20),findViewById(R.id.tvhourdegree21),findViewById(R.id.tvhourdegree22),findViewById(R.id.tvhourdegree23),findViewById(R.id.tvhourdegree24))
        val uri: Uri
        val appTemp = findViewById<TextView>(R.id.tvAppT)
        val humid = findViewById<TextView>(R.id.tvhumidPer)
        val windSpeed = findViewById<TextView>(R.id.tvwindSpeed)
        val uv = findViewById<TextView>(R.id.ultra_light)
        val dis = findViewById<TextView>(R.id.distance)
        val airPressure = findViewById<TextView>(R.id.hPa)
        val wind_dir = findViewById<TextView>(R.id.tvwind)

        if("rain" in current.condition.text){
            if(current.is_day==1){
                uri = parse("android.resource://"+packageName+"/"+R.raw.pexels2)
                videoView.setVideoURI(uri)
                videoView.start()
                back.setBackgroundColor(ContextCompat.getColor(this, R.color.background_dayRain))
            }
        }
        else{
            if(current.is_day==1){
                uri = parse("android.resource://"+packageName+"/"+R.raw.partlycloudly_2)
            }
            else{
                uri = parse("android.resource://"+packageName+"/"+R.raw.night)
                loc.setTextColor(ContextCompat.getColor(this, R.color.white))
                temp.setTextColor(ContextCompat.getColor(this, R.color.white))
                date.setTextColor(ContextCompat.getColor(this, R.color.white))
                status.setTextColor(ContextCompat.getColor(this, R.color.white))
                deg.setTextColor(ContextCompat.getColor(this, R.color.white))

                back.setBackgroundColor(ContextCompat.getColor(this, R.color.background_night))
            }
            videoView.setVideoURI(uri)
            videoView.start()
        }

        var c = findViewById<TextView>(R.id.tvdegrees)
        loc.text = location.name
        temp.text = current.temp_c.toInt().toString()
        var month=""
        when(location.localtime.subSequence(5,7)){
            "01"->month="Jan"
            "02"->month="Feb"
            "03"->month="March"
            "04"->month="April"
            "05"->month="May"
            "06"->month="Jun"
            "07"->month="Jul"
            "08"->month="Aug"
            "09"->month="Sep"
            "10"->month="Oct"
            "11"->month="Nov"
            "12"->month="Dec"
        }
        date.text = "$month ${location.localtime.subSequence(8,10)}"
        status.text = current.condition.text

        appTemp.text = "${ current.temp_c.toInt() } C"
        humid.text = "${ current.humidity.toString() } %"
        windSpeed.text = "${current.wind_kph} Km/h"
        dis.text = "${current.vis_km} Km"
        airPressure.text = "${current.pressure_mb} hPa"
        wind_dir.text = "${current.wind_dir} wind"

        var index = current.last_updated.subSequence(11,13).toString().toInt()
        Log.i("MyI","${location.localtime}")
        var i =0
        while (forecastList.hasNext()){
            val day = forecastList.next()
            if(index>=24){
                index = current.last_updated.subSequence(11,13).toString().toInt()
                var t=0
                while(t<index){
                    var hour = day.hour[t]
                    var t1 = listofIds[i]
                    var t1_temp = listofIdsforTemp[i]
                    t1.text = if(hour.time.subSequence(11, 13).toString().toInt()<12) "${hour.time.subSequence(11, 13)}:00 AM" else "${hour.time.subSequence(11, 13)}:00 PM"
                    t1_temp.text = "${hour.temp_c} C"
                    var imgg = imgList[i]
                    if("rain" in hour.condition.text){
                        var urI = parse("android.resource://"+packageName+"/"+R.drawable.raining)
                        imgg.setImageURI(urI)
                    }
                    else if("thunder" in hour.condition.text.lowercase()){
                        var urI = parse("android.resource://"+packageName+"/"+R.drawable.raining_lightning)
                        imgg.setImageURI(urI)
                    }
                    else if("partly" in hour.condition.text.lowercase()){
                        if(hour.is_day==1) {
                            var urI =
                                parse("android.resource://" + packageName + "/" + R.drawable.day_sky)
                            imgg.setImageURI(urI)
                        }
                        else {
                            var urI =
                                parse("android.resource://" + packageName + "/" + R.drawable.night_sky)
                            imgg.setImageURI(urI)
                        }
                    }
                    t++
                    i++
                }
            }
            else{
                while (index < 24) {
                    var hour = day.hour[index]
                    var t1 = listofIds[i]
                    var t1_temp = listofIdsforTemp[i]
                    t1.text = if(hour.time.subSequence(11, 13).toString().toInt()<12) "${hour.time.subSequence(11, 13)}:00 AM" else "${hour.time.subSequence(11, 13)}:00 PM"
                    t1_temp.text = "${hour.temp_c} C"
                    var imgg = imgList[i]
                    if("rain" in hour.condition.text.lowercase()){
                        var urI = parse("android.resource://"+packageName+"/"+R.drawable.raining)
                        imgg.setImageURI(urI)
                    }
                    else if("thunder" in hour.condition.text.lowercase()){
                        var urI = parse("android.resource://"+packageName+"/"+R.drawable.raining_lightning)
                        imgg.setImageURI(urI)
                    }
                    else if("partly" in hour.condition.text.lowercase()){
                        if(hour.is_day==1) {
                            var urI =
                                parse("android.resource://" + packageName + "/" + R.drawable.day_sky)
                            imgg.setImageURI(urI)
                        }
                        else {
                            var urI =
                                parse("android.resource://" + packageName + "/" + R.drawable.night_sky)
                            imgg.setImageURI(urI)
                        }
                    }
                    index++
                    i++
                }
            }
        }
    }
}
