package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    var timeLimit: Int = 0
    private val colorList = listOf(Color.RED, Color.YELLOW, Color.BLUE)
    private var colorIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var stopwatchActive = false
        val initialColor = binding.textView.textColors

        binding.progressBar.indeterminateTintList = ColorStateList.valueOf(colorList[colorIndex])

        binding.startButton.setOnClickListener {
            if (!stopwatchActive) {
            stopwatchActive = true
            binding.settingsButton.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
            handler.postDelayed(updateTime, 1000)
            }
        }

        binding.resetButton.setOnClickListener {
            stopwatchActive = false
            binding.settingsButton.isEnabled = true
            binding.textView.setTextColor(initialColor)
            handler.removeCallbacks(updateTime)
            binding.textView.text = getText(R.string.stopWatchInitialText)
            binding.progressBar.visibility = View.GONE
        }

        binding.settingsButton.setOnClickListener {
            val limitInput = LayoutInflater.from(this).inflate(R.layout.settings_menu, null, false)
            AlertDialog.Builder(this)
                .setTitle(R.string.settingsDescription)
                .setView(limitInput)
                .setPositiveButton(android.R.string.ok) {
                        _, _ -> timeLimit = limitInput.findViewById<EditText>(R.id.upperLimitEditText).text.toString().toInt()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateTime)
    }

    private val updateTime: Runnable = object : Runnable {
        override fun run() {
            var elapsedTime: Int = binding.textView.text.split(":").run {
                first().toInt() * 60 + last().toInt()
            }
            colorIndex = (colorIndex + 1) % colorList.size
            binding.progressBar.indeterminateTintList = ColorStateList.valueOf(colorList[colorIndex])
            elapsedTime++
            if (timeLimit in 1..elapsedTime) binding.textView.setTextColor(Color.RED)
            "%02d:%02d".format(elapsedTime / 60, elapsedTime % 60).also { binding.textView.text = it }
            handler.postDelayed(this, 1000)
        }
    }

}