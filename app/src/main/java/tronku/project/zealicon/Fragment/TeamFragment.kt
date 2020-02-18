package tronku.project.zealicon.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.home_fragment.*

import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Viewmodel.MyPlaylistViewModel

class TeamFragment : Fragment() {

    companion object {
        fun newInstance() = TeamFragment()
    }

    private lateinit var viewModel: MyPlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyPlaylistViewModel::class.java)
        // TODO: Use the ViewModel

    }


}
