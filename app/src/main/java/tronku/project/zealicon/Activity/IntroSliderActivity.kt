package tronku.project.zealicon.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import kotlinx.android.synthetic.main.activity_intro_slider.*
import tronku.project.zealicon.Adapter.ViewPagerIntroAdapter
import tronku.project.zealicon.R


class IntroSliderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)

        var pref = applicationContext.getSharedPreferences("zealiconPref", 0)
        var editor: SharedPreferences.Editor = pref.edit()


        val viewPagerIntroAdapter = ViewPagerIntroAdapter(supportFragmentManager)
        viewPagerIntro.adapter = viewPagerIntroAdapter

        dotsIndicator.setViewPager(viewPagerIntro)

        fabIntro.setOnClickListener {
            val page: Int = viewPagerIntro.currentItem
            when (page) {
                0 -> viewPagerIntro.currentItem = 1
                1 -> {
                    viewPagerIntro.currentItem = 2
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }

                2 -> {
                    editor.putBoolean("firstLaunch", false)
                    editor.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }

        viewPagerIntro.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0 || position == 1 ) {
                    fabIntro.visibility = View.VISIBLE
                    fabIntro.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp)
                } else if (position == 2) {
                    fabIntro.setImageResource(R.drawable.ic_check_white_24dp)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}
