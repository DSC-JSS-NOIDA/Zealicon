package tronku.project.zealicon.Activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.android.synthetic.main.activity_player.*
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        addEffects()
        addClickEvents()
    }

    fun addEffects() {
        AnimUtils.setTouchEffect(buttonNext)
        AnimUtils.setTouchEffect(buttonBack)
        AnimUtils.setTouchEffect(buttonShare)
    }

    fun addClickEvents() {
        val rotator = ObjectAnimator.ofFloat(trackImage, View.ROTATION, 0f, 360f)
        rotator.duration = 6000
        rotator.repeatCount = ObjectAnimator.INFINITE
        rotator.interpolator = LinearInterpolator()
        rotator.start()

        buttonPlay.setOnClickListener {
            buttonPause.visibility = View.VISIBLE
            buttonPlay.visibility = View.GONE
            rotator.resume()
            waveView.play()
        }

        buttonPause.setOnClickListener {
            buttonPlay.visibility = View.VISIBLE
            buttonPause.visibility = View.GONE
            rotator.pause()
            waveView.pause()
        }

        backButton.setOnClickListener {
            findNavController(R.id.backButton).navigateUp()
        }
    }

}
