package tronku.project.zealicon.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_team.*
import tronku.project.zealicon.Adapter.TeamAdapter
import tronku.project.zealicon.Model.TeamModel

import tronku.project.zealicon.R
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

        val managementCommitee: ArrayList<TeamModel> = ArrayList()
        managementCommitee.add(TeamModel(R.drawable.m1, "Dr. Gurulingappa M Patil", "Chairman"))
        managementCommitee.add(TeamModel(R.drawable.m2, "Dr. Prashant Chauhan", "Convener"))


        val coreCommitee: ArrayList<TeamModel> = ArrayList()
        coreCommitee.add(TeamModel(R.drawable.c1, "Kushagra Bhardwaj", "Festival Secretary"))
        coreCommitee.add(TeamModel(R.drawable.c2, "Devanshu Monga", "Festival Secretary"))
        coreCommitee.add(TeamModel(R.drawable.c4, "Suryash Udit", "Festival Co-Secretary"))
        coreCommitee.add(TeamModel(R.drawable.c5, "Kunal Vishnoi", "Technical Head"))
        coreCommitee.add(TeamModel(R.drawable.c6, "Shubham Rana", "Sponsorship Head"))
        coreCommitee.add(TeamModel(R.drawable.c7, "Nancy Poddar", "Deputy Sponsorship Head"))


        val techCommitee: ArrayList<TeamModel> = ArrayList()
        techCommitee.add(TeamModel(R.drawable.t1, "Rohit Jakhmola", "Tech Lead"))
        techCommitee.add(TeamModel(R.drawable.t2, "Shobhit Agarwal", "Tech Lead"))
        techCommitee.add(TeamModel(R.drawable.t3, "Anant Jain", "Tech Lead"))
        techCommitee.add(TeamModel(R.drawable.t4, "Shubham Singh", "Design Lead"))
        techCommitee.add(TeamModel(R.drawable.t7, "Shubham Pathak", "App Developer"))
        techCommitee.add(TeamModel(R.drawable.t13, "Dheeraj Kotwani", "App Developer"))

        val managementAdapter: TeamAdapter = TeamAdapter(managementCommitee)
        val coreTeamAdapter: TeamAdapter = TeamAdapter(coreCommitee)
        val techTeamAdapter: TeamAdapter = TeamAdapter(techCommitee)

        recyclerViewManagementCommitee.adapter = managementAdapter
        recyclerCoreTeamCommitee.adapter = coreTeamAdapter
        recyclerTechTeamCommitee.adapter = techTeamAdapter

    }


}
