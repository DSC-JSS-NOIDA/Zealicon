package tronku.project.zealicon.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_play_list.*
import kotlinx.android.synthetic.main.home_fragment.*
import tronku.project.zealicon.Adapter.TracksAdapter
import tronku.project.zealicon.Model.EventTrack

import tronku.project.zealicon.R

/**
 * A simple [Fragment] subclass.
 */
class PlayListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistCategory.isSelected = true

        getPlaylist()
    }

    private fun getPlaylist() {
        val adapter = TracksAdapter()
        val trackList = ArrayList<EventTrack>()
        trackList.add(EventTrack(1, "Line up", "GPS based event", "12:00 PM", "4:00 PM", "Play it On!"))
        trackList.add(EventTrack(2, "Doodle", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(3, "asfas", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(4, "htere", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(5, "jerte", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(6, "abdbc", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(7, "masfa", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(8, "pqqoep", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(9, "lalks", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        trackList.add(EventTrack(10, "mangas", "Designing event", "2:00 PM", "5:00 PM", "Play it On!"))
        adapter.submitList(trackList)
        trackRecyclerView.adapter = adapter
    }
}
