package com.example.weatherapp

import android.Manifest
import android.R.attr.apiKey
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.net.Uri.parse
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Response
import java.util.*


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
    private lateinit var Horizrec:RecyclerView
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private var ll=""
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

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
        val ref = findViewById<ImageButton>(R.id.refresh)
        val cons = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        sf = getSharedPreferences("Users Data", MODE_PRIVATE)
        editor = sf.edit()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        rec = findViewById<RecyclerView>(R.id.rcView)
        rec.layoutManager = LinearLayoutManager(this)
        Horizrec = findViewById<RecyclerView>(R.id.rcHorizontalView)
        Horizrec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getLocation()
        ref.setOnClickListener{
            getLocation()
        }
    }
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                Log.i("kk","Till here")
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: android.location.Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)!!
                        ll=list[0].locality
                        editor.apply {
                            putString("User_location",ll)
                            commit() // if we don't commit then the values wont be saved
                        }
                        Log.i("dicc",ll)
                    }
                }
                retrofitGetSequence()
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        editor.apply {
            putString("User_location",ll)
            commit() // if we don't commit then the values wont be saved
        }
    }

    override fun onResume() {
        super.onResume()
        ll = sf.getString("User_location","").toString()
    }



    private fun forecastclicked(forecast : Forecastday){

    }
    private fun retrofitGetSequence(){
        videoView.setOnPreparedListener{
                mediaPlayer -> mediaPlayer.isLooping = true
        }

        val retService = RetrofitInstance.getRetrofitInstance().create(currentW::class.java)
        if(ll==""){
            ll = sf.getString("User_location","").toString()
        }
        val responseLiveData : LiveData<Response<Forecast>> = liveData {
            val response = retService.getCurrentWeather("4e7e70617e264e0eb40112916230305",ll,2,"no","no")
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val cur = it.body();
            if(cur!=null){
                current= cur.current
                location = cur.location
                forecastList = cur.forecast.forecastday.listIterator()
                var hourday1 = forecastList.next().hour
                var hourday2 = forecastList.next().hour
                var index = current.last_updated.subSequence(11,13).toString().toInt()
                Horizrec.adapter = HorizontalViewAdapter(
                    ArrayList<Hour>().apply {
                        addAll(hourday1.subList(index, hourday1.size))
                        addAll(hourday2.subList(0, index+1))
                    },index,packageName,current.is_day
                )
                rec.adapter = ViewAdapter(
                    cur.forecast.forecastday,current
                ){ selectedItem: Forecastday ->
                    forecastclicked(selectedItem)
                }
                setAll(current,location,forecastList)
            }
        })
    }
    private fun setAll(current: Current, location: Location, forecastList : ListIterator<Forecastday>){
        val deg = findViewById<TextView>(R.id.tvdegrees)
        val back = findViewById<ConstraintLayout>(R.id.weather)
        val uri: Uri
        val appTemp = findViewById<TextView>(R.id.tvAppT)
        val humid = findViewById<TextView>(R.id.tvhumidPer)
        val windSpeed = findViewById<TextView>(R.id.tvwindSpeed)
        val uv = findViewById<TextView>(R.id.ultra_light)
        val dis = findViewById<TextView>(R.id.distance)
        val airPressure = findViewById<TextView>(R.id.hPa)
        val wind_dir = findViewById<TextView>(R.id.tvwind)
        val wed_details = findViewById<TextView>(R.id.textView9)
        val list_of_cards = listOf<CardView>(findViewById(R.id.AppTemp),findViewById(R.id.card_humid),findViewById(R.id.wind),findViewById(R.id.UV),findViewById(R.id.visibility),findViewById(R.id.AirPressure))
        val list_of_texts = listOf<TextView>(deg,appTemp,humid,windSpeed,uv,dis,airPressure,wind_dir,wed_details,loc,temp,date,status,findViewById(R.id.tvappTemp),findViewById(R.id.tvhumidity),findViewById(R.id.tvwind),findViewById(R.id.ultra),findViewById(R.id.tvVisibility),findViewById(R.id.tvairPressure))
        if("rain" in current.condition.text){
            if(current.is_day==1){
                uri = parse("android.resource://"+ packageName +"/"+R.raw.pexels2)
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
                back.setBackgroundColor(ContextCompat.getColor(this,
                    android.R.color.system_accent2_800
                ))
                list_of_cards.forEach({e -> e.setCardBackgroundColor(ContextCompat.getColor(this, android.R.color.system_accent2_800))})
                list_of_texts.forEach({e -> e.setTextColor(ContextCompat.getColor(this, R.color.white))})
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
    }
}
