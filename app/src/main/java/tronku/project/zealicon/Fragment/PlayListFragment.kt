package tronku.project.zealicon.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_play_list.*
import tronku.project.zealicon.Adapter.PlayerTarget
import tronku.project.zealicon.Adapter.TracksAdapter
import tronku.project.zealicon.Model.EventTrack

import tronku.project.zealicon.R

/**
 * A simple [Fragment] subclass.
 */
class PlayListFragment : Fragment() {

    var day: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        day = arguments?.getInt("day", 1) ?: 1
        updateUI()
        playlistCategory.isSelected = true
        waveView.play()
        getPlaylist()
    }

    private fun updateUI() {
        when(day) {
            1 -> {
                dayName.text = "ONE"
                dayImg.setImageResource(R.drawable.day1)
                dayShade.setImageResource(R.drawable.curved_card_blue)
                playlistName.text = "Day ONE"
                playlistDate.text = "24 March, 2020"
            }
            2 -> {
                dayName.text = "TWO"
                dayImg.setImageResource(R.drawable.day2)
                dayShade.setImageResource(R.drawable.curved_card_purple)
                playlistName.text = "Day TWO"
                playlistDate.text = "25 March, 2020"
            }
            3 -> {
                dayName.text = "THREE"
                dayImg.setImageResource(R.drawable.day3)
                dayShade.setImageResource(R.drawable.curved_card_green)
                playlistName.text = "Day THREE"
                playlistDate.text = "26 March, 2020"
            }
            4 -> {
                dayName.text = "FOUR"
                dayImg.setImageResource(R.drawable.day4)
                dayShade.setImageResource(R.drawable.curved_card_yellow)
                playlistName.text = "Day FOUR"
                playlistDate.text = "27 March, 2020"
            }
        }
    }

    private fun getPlaylist() {
        val adapter = TracksAdapter(PlayerTarget.PLAYLIST)
        val trackList = ArrayList<EventTrack>()
        trackList.add(EventTrack(1,
            "Line-up",
            "GPS based event",
            2, "Upcoming", 5, 2000, 1000,
            "Shubham Pathak",
            "8005709570",
            1, 2))
        trackList.add(EventTrack(1,
            "Line-up",
            "GPS based event",
            2, "Upcoming", 5, 2000, 1000,
            "Shubham Pathak",
            "8005709570",
            1, 2))
        trackList.add(EventTrack(1,
            "Line-up",
            "GPS based event",
            2, "Upcoming", 5, 2000, 1000,
            "Shubham Pathak",
            "8005709570",
            1, 2))
        trackList.add(EventTrack(1,
            "Line-up",
            "GPS based event",
            2, "Upcoming", 5, 2000, 1000,
            "Shubham Pathak",
            "8005709570",
            1, 2))
//        adapter.submitList(trackList)
        trackRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        waveView.pause()
    }
}
