package tronku.project.zealicon.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.home_fragment.*
import tronku.project.zealicon.Adapter.PlayerTarget
import tronku.project.zealicon.Adapter.TracksAdapter
import tronku.project.zealicon.Database.RoomDB

import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by lazy { HomeViewModel() }
    private val db by lazy { RoomDB(context!!) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        setObserver()
        //TODO SET DYNAMIC DAY
        viewModel.getUpcomingHits(db, 1)
    }

    private fun setClickListeners() {
        val duration = 300L
        day_one_list.setOnClickListener {
            AnimUtils.setClickAnimation(day_one_list, R.id.action_home_to_playListFragment, getArgs(1), duration)
        }

        day_two_list.setOnClickListener {
            AnimUtils.setClickAnimation(day_two_list, R.id.action_home_to_playListFragment, getArgs(2), duration)
        }

        day_three_list.setOnClickListener {
            AnimUtils.setClickAnimation(day_three_list, R.id.action_home_to_playListFragment, getArgs(3), duration)
        }

        day_four_list.setOnClickListener {
            AnimUtils.setClickAnimation(day_four_list, R.id.action_home_to_playListFragment, getArgs(4), duration)
        }
    }

    private fun setObserver() {
        val adapter = TracksAdapter(PlayerTarget.HOME)
        upcomingHitsRecycler.adapter = adapter

        //TODO ADD DYNAMIC DAY
        viewModel.upcomingList.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            } else {
                it.shuffle()
                adapter.submitList(it.subList(0, if (it.size < 5) it.size else 5))
            }
        })
    }

    private fun getArgs(day: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("day", day)
        return bundle
    }

}
