package tronku.project.zealicon.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_play_list.*
import kotlinx.android.synthetic.main.my_playlist_fragment.*
import tronku.project.zealicon.Adapter.PlayerTarget
import tronku.project.zealicon.Adapter.TracksAdapter
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.R
import tronku.project.zealicon.Viewmodel.MyPlaylistViewModel
import java.util.*
import kotlin.collections.ArrayList

class MyPlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = MyPlaylistFragment()
    }

    private val db by lazy { RoomDB(context!!) }
    private val viewModel by lazy { MyPlaylistViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_playlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {
        val adapter = TracksAdapter(PlayerTarget.MY_PLAYLIST)
        playlistRecyclerView.adapter = adapter
        viewModel.getMyPlaylist(db).observe(viewLifecycleOwner, Observer {
            val tracks = ArrayList<EventTrackDB>(it)
            if (tracks.isNullOrEmpty()) {
                Toast.makeText(context, "No tracks", Toast.LENGTH_SHORT).show()
                myPlaylistLayout.visibility = View.GONE
            } else {
                myPlaylistLayout.visibility = View.VISIBLE
                adapter.submitList(tracks)
                updateSummary(tracks)
            }
        })
    }

    private fun updateSummary(list: ArrayList<EventTrackDB>) {
        val categories = TreeSet<String>()
        list.forEach {
            categories.add(it.category!!)
        }
        playlistSummary.text = "${list.size} tracks   •   ${categories.size} categories"
    }

}
