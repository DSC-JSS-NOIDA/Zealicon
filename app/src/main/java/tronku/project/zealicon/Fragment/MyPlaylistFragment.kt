package tronku.project.zealicon.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.home_fragment.*

import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Viewmodel.MyPlaylistViewModel

class MyPlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = MyPlaylistFragment()
    }

    private lateinit var viewModel: MyPlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyPlaylistViewModel::class.java)
        // TODO: Use the ViewModel
        addEffects()
        clickEvents()

    }

    private fun addEffects(){

        AnimUtils.setTouchEffect(buttonBack)
        AnimUtils.setTouchEffect(buttonNext)
        AnimUtils.setTouchEffect(buttonInfo)
//        AnimUtils.setTouchEffect(buttonPause)
//        AnimUtils.setTouchEffect(buttonPlay)
        AnimUtils.setTouchEffect(buttonShare)

    }

    private fun clickEvents(){

        buttonPlay.setOnClickListener {
            buttonPause.visibility = View.VISIBLE
            buttonPlay.visibility = View.GONE
            lottieAnnimation.resumeAnimation()
        }

        buttonPause.setOnClickListener {
            buttonPlay.visibility = View.VISIBLE
            buttonPause.visibility = View.GONE
            lottieAnnimation.pauseAnimation()
        }

//        buttonLike.setOnClickListener {
//
//            buttonLike.visibility = View.GONE
//            buttonUnlike.visibility = View.VISIBLE
//
//        }
//
//        buttonUnlike.setOnClickListener {
//
//            buttonLike.visibility = View.VISIBLE
//            buttonUnlike.visibility = View.GONE
//
//        }
    }


}
