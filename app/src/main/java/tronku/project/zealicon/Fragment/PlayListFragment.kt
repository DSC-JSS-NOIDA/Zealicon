package tronku.project.zealicon.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_play_list.*
import tronku.project.zealicon.Adapter.PlayerTarget
import tronku.project.zealicon.Adapter.TracksAdapter
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrackDB

import tronku.project.zealicon.R
import tronku.project.zealicon.Viewmodel.PlaylistViewModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class PlayListFragment : Fragment() {

    var day: Int = 1
    private val viewModel by lazy { PlaylistViewModel() }
    private val db by lazy { RoomDB(context!!) }

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

        setObserver()
        viewModel.getPlaylist(db, day)
    }

    private fun setObserver() {
        val adapter = TracksAdapter(PlayerTarget.PLAYLIST)
        trackRecyclerView.adapter = adapter
        viewModel.playlist.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(context, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
            } else {
                adapter.submitList(it.shuffled())
                updateSummary(it)
            }
        })
    }

    private fun updateSummary(list: ArrayList<EventTrackDB>) {
        val categories = TreeSet<String>()
        list.forEach {
            categories.add(it.category!!)
        }
        daySummary.text = "${list.size} tracks   •   ${categories.size} categories"
        var categ = ""
        categories.forEach { categ += "$it | " }
        playlistCategory.text = categ
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

    override fun onDestroyView() {
        super.onDestroyView()
        waveView.pause()
    }
}
