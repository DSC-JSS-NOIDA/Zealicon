package tronku.project.zealicon.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import tronku.project.zealicon.Fragment.FragmentIntro.FragmentIntro1
import tronku.project.zealicon.Fragment.FragmentIntro.FragmentIntro2
import tronku.project.zealicon.Fragment.FragmentIntro.FragmentIntro3
import tronku.project.zealicon.Fragment.FragmentIntro.FragmentIntro4

class ViewPagerIntroAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentIntro1()
            }
            1 -> {
                FragmentIntro2()
            }
            2 -> {
                FragmentIntro3()
            }
//            3 -> {
//                FragmentIntro4()
//            }
            else -> FragmentIntro1()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}