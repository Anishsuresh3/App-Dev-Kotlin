package com.example.audiovideo

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private var mediaPlayer : MediaPlayer? = null
    private lateinit var runnable : Runnable
    private lateinit var seekbar : SeekBar
    private lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mediaPlayer = MediaPlayer.create(this,R.raw.universe)
        seekbar = findViewById(R.id.seekBar)
        handler = Handler(Looper.getMainLooper())
        val playbutton = findViewById<FloatingActionButton>(R.id.fabplay)
        playbutton.setOnClickListener {
            if (mediaPlayer==null){
                mediaPlayer = MediaPlayer.create(this,R.raw.stereo)
                initSeekBar()
            }
            mediaPlayer?.start()

        }
        val pausebutton = findViewById<FloatingActionButton>(R.id.fabpause)
        pausebutton.setOnClickListener {
            mediaPlayer?.pause()
        }
        val stopbutton = findViewById<FloatingActionButton>(R.id.fabstop)
        stopbutton.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekbar.progress = 0
        }
    }
    private fun initSeekBar(){
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        val tvplaying = findViewById<TextView>(R.id.tvplay)
        val tvdue = findViewById<TextView>(R.id.tvdue)
        seekbar.max = mediaPlayer!!.duration // not null !!
        runnable = Runnable {
            seekbar.progress = mediaPlayer!!.currentPosition
            val playtime = mediaPlayer!!.currentPosition/1000
            tvplaying.text = "$playtime sec"
            val dur = mediaPlayer!!.duration/1000
            tvdue.text = "${dur-playtime} sec"
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }
}