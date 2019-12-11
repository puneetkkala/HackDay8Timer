package xyz.kalapuneet.hackday8timer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PowerManager
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        val wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.FULL_WAKE_LOCK, "App::WakeLockTag").apply {
                acquire()
            }
        }

        val timer = object : CountDownTimer(90000, 1000) {
            override fun onFinish() {
                timeTv.text = "Time Up"
                timeTv.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.elegantWhite))
                timeTv.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.googleRed))
            }

            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = (millisUntilFinished / 1000L)
                if (timeLeft in listOf(10L, 20L, 30L)) {
                    timeTv.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.elegantWhite))
                    timeTv.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.elegantBlack))
                } else {
                    timeTv.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.elegantBlack))
                    timeTv.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.elegantWhite))
                }
                timeTv.text = "$timeLeft"
            }
        }
        timer.start()

        timeTv.setOnClickListener {
            if (timeTv.text.toString() == "Time Up") {
                timeTv.setTextColor(ContextCompat.getColor(this, R.color.elegantBlack))
                container.setBackgroundColor(ContextCompat.getColor(this, R.color.elegantWhite))
                timer.start()
            }
        }
    }
}
