package org.hyperskill.stopwatch

import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private enum class ProgressBarColor(val color: Int) {
        FIRST(R.color.first),
        SECOND(R.color.second),
        THIRD(R.color.third);

        fun nextColor(): ProgressBarColor {
            return when(this.ordinal) {
                THIRD.ordinal -> FIRST
                else -> values()[this.ordinal + 1]
            }
        }
    }
    private var changingColor = ProgressBarColor.SECOND

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var stopwatchActive = false

        binding.progressBar.indeterminateTintList = ColorStateList.valueOf(changingColor.color)

        binding.startButton.setOnClickListener {
            if (!stopwatchActive) {
                stopwatchActive = true
                binding.progressBar.visibility = View.VISIBLE
                handler.postDelayed(updateTime, 1000)
            }
        }

        binding.resetButton.setOnClickListener {
            stopwatchActive = false
            handler.removeCallbacks(updateTime)
            binding.textView.text = getText(R.string.stopWatchInitialText)
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateTime)
    }

    private val updateTime: Runnable = object : Runnable {
        override fun run() {
            var (min, sec) = binding.textView.text.split(":").map {it.toInt()}
            changingColor = changingColor.nextColor()
            binding.progressBar.indeterminateTintList = ColorStateList.valueOf(changingColor.color)
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