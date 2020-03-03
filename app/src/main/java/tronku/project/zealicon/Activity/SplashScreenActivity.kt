package tronku.project.zealicon.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.preference.PreferenceManager
import tronku.project.zealicon.R

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        val firstLaunch: Boolean = pref.getBoolean("firstLaunch", true)

        Handler().postDelayed({
            if (firstLaunch.equals(true)) {
                startActivity(Intent(this, IntroSliderActivity::class.java))
                finish()
            }
            else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, SPLASH_TIME_OUT)
    }
}
