package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import org.hyperskill.stopwatch.databinding.ActivityMainBinding

private const val CHANNEL_ID = "org.hyperskill"
private const val NOTIFICATION_ID = 393939

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    var timeLimit = 0
    var elapsedTime = 0
    private val colorList = listOf(Color.RED, Color.YELLOW, Color.BLUE)
    private var colorIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Notification Channel Name"
            val descriptionText = "Notification Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        var stopwatchActive = false
        val initTextColor = binding.textView.textColors

        binding.startButton.setOnClickListener {
            if (!stopwatchActive) {
                stopwatchActive = true
                startTimer()
            }
        }

        binding.resetButton.setOnClickListener {
            stopwatchActive = false
            binding.textView.setTextColor(initTextColor)
            resetTimer()
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
        handler.removeCallbacks(runTimer)
    }

    private val runTimer: Runnable = object : Runnable {
        override fun run() {
            colorIndex = (colorIndex + 1) % colorList.size
            binding.progressBar.indeterminateTintList = ColorStateList.valueOf(colorList[colorIndex])
            elapsedTime++
            if (timeLimit in 1..elapsedTime) {
                onLimit()
            }
            "%02d:%02d".format(elapsedTime / 60, elapsedTime % 60).also { binding.textView.text = it }
            handler.postDelayed(this, 1000)
        }
    }

    private fun startTimer() {
        binding.settingsButton.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        handler.postDelayed(runTimer, 1000)
    }

    private fun resetTimer() {
        elapsedTime = 0
        binding.settingsButton.isEnabled = true
        binding.textView.text = getText(R.string.stopWatchInitialText)
        binding.progressBar.visibility = View.GONE
        handler.removeCallbacks(runTimer)
    }

    private fun onLimit() {
        binding.textView.setTextColor(Color.RED)
        notificationBuilder()
    }

    private fun notificationBuilder() {
        val builtNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
            .setSmallIcon(R.drawable.test_icon)
            .setContentTitle("content title")
            .setContentText("content text")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .build()

        builtNotification.flags = (Notification.FLAG_INSISTENT or Notification.FLAG_ONLY_ALERT_ONCE)

        val nM = this@MainActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nM.notify(NOTIFICATION_ID, builtNotification)
    }
}