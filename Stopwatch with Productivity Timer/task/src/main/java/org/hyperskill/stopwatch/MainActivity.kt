package org.hyperskill.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.stopwatch.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var stopwatchActive = false

        binding.startButton.setOnClickListener {
            if (!stopwatchActive) {
                stopwatchActive = true
                handler.postDelayed(updateTime, 1000)
            }
        }

        binding.resetButton.setOnClickListener {
            stopwatchActive = false
            handler.removeCallbacks(updateTime)
            binding.textView.text = getText(R.string.stopWatchInitialText)
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateTime)
    }

    private val updateTime: Runnable = object : Runnable {
        override fun run() {
            var (min, sec) = binding.textView.text.split(":").map {it.toInt()}
            sec++
            if (sec >= 60) {
                min = sec/60
                sec %= 60
            }
            "%02d:%02d".format(min, sec).also { binding.textView.text = it }
            handler.postDelayed(this, 1000)
        }
    }
}