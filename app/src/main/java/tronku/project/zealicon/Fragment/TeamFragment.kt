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
        managementCommitee.add(TeamModel("https://i.ibb.co/9ZYPZfH/p4.png", "Dr. Gurulingappa M Patil", "Chairman"))
        managementCommitee.add(TeamModel("https://i.ibb.co/HCr2wd2/p3.png", "Dr. Prashant Chauhan", "Convener"))


        val coreCommitee: ArrayList<TeamModel> = ArrayList()
        coreCommitee.add(TeamModel("https://i.ibb.co/92ptbZ5/p29.png", "Kushagra Bhardwaj", "Festival Secretary"))
        coreCommitee.add(TeamModel("https://i.ibb.co/t88Q3cD/p25.png", "Devanshu Monga", "Festival Secretary"))
        coreCommitee.add(TeamModel("https://i.ibb.co/Kw7zCXm/p33.png", "Raman Khatri", "Festival Co-Secretary"))
        coreCommitee.add(TeamModel("https://i.ibb.co/CKPjKgD/p31.png", "Suryash Udit", "Festival Co-Secretary"))
        coreCommitee.add(TeamModel("https://i.ibb.co/PGzVfDp/p14-1.png", "Kunal Vishnoi", "Technical Head"))
        coreCommitee.add(TeamModel("https://i.ibb.co/QMzBGvZ/p26-1.png", "Shubham Rana", "Sponsorship Head"))
        coreCommitee.add(TeamModel("https://i.ibb.co/DfDjnDH/p8.png", "Nancy Poddar", "Deputy Sponsorship Head"))
        coreCommitee.add(TeamModel( "https://i.ibb.co/JycRWyd/p27.png",
           "Karan Bains",
        "Artist & Stage Head "))
        coreCommitee.add(TeamModel("https://i.ibb.co/1zS0Ctm/p5.png",
            "Abhinav Singh",
            "Cultural Head"))
        coreCommitee.add(TeamModel("https://i.ibb.co/9rKSjcP/p28.png",
            "Shubham Singh",
            "Security Head"))
        coreCommitee.add(TeamModel("https://i.ibb.co/cXKJMP9/pp8.png",
            "Prakhar Agnihotri",
            "Deputy Security Head"))
        coreCommitee.add(TeamModel( "https://i.ibb.co/g9wkHPX/p1.png",
            "Akarsh Neeraj",
             "Media & Photography Head"))
        coreCommitee.add(TeamModel( "https://i.ibb.co/JQRVXNP/p13.png",
            "Kamal Meghani",
            "Management Head "))
        coreCommitee.add(TeamModel("https://i.ibb.co/L1vQm3J/p7.png",
            "Amit Singh",
            "Creative Head"))
        coreCommitee.add(TeamModel("https://i.ibb.co/9cTR9q2/p24.png",
            "Raja Yadav",
            "Co-Creative Head  "))
        coreCommitee.add(TeamModel("https://i.ibb.co/qd26SNp/p10.png",
            "Simran Gupta",
            "Student Welfare & Outreach Head"))
        coreCommitee.add(TeamModel( "https://i.ibb.co/0Mhv97s/p32.png",
            "Piyush Kumar",
            "Scheduling Head  "))
        coreCommitee.add(TeamModel( "https://i.ibb.co/n0gJWJ0/p2.png",
            "Vishal Dixit",
            "Marketing Head"))
        coreCommitee.add(TeamModel( "https://i.ibb.co/C79Kbdv/p6.png",
            "Ishita",
            "Marketing Head"))
        coreCommitee.add(TeamModel( "https://i.ibb.co/5GYxGMc/p36.png",
            "Akriti Sundaram",
            "P.A. System Head"))
        coreCommitee.add(TeamModel("https://i.ibb.co/4VppMwn/p9.png",
            "Tijil Shandilya",
            "Resource Head"))



        val techCommitee: ArrayList<TeamModel> = ArrayList()
        techCommitee.add(TeamModel("https://i.ibb.co/640S8Rj/p34-1.png", "Rohit Jakhmola", "Tech Lead"))
        techCommitee.add(TeamModel("https://i.ibb.co/tc7R6Ct/pp7.png", "Shobhit Agarwal", "Tech Lead"))
        techCommitee.add(TeamModel("https://i.ibb.co/kQNRLCV/p16.png", "Anant Jain", "Tech Lead"))
        techCommitee.add(TeamModel("https://i.ibb.co/cTW9Wzv/pp6.png", "Shubham Singh", "Design Lead"))
        techCommitee.add(TeamModel( "https://i.ibb.co/C2V3TDz/p30.png",
             "Kumar Rohit",
            "Design Lead"))
        techCommitee.add(TeamModel ("https://i.ibb.co/XJxBLD7/p17.png", "Shubham Pathak", "App Developer"))
        techCommitee.add(TeamModel( "https://i.ibb.co/82K4TW7/p9.png",
            "Sourabh Singh",
            "Developer"))
        techCommitee.add(TeamModel("https://i.ibb.co/6mMf4SX/p22.png", "Dheeraj Kotwani", "App Developer"))
        techCommitee.add(TeamModel( "https://i.ibb.co/sKQgjgj/p35.png",
            "Sarthak Chauhan",
            "Designer"))
        techCommitee.add(TeamModel("https://i.ibb.co/XY4H8Tm/p15.png",
            "Devansh Chaudhary",
            "Designer"))
        techCommitee.add(TeamModel("https://i.ibb.co/Jc2GqXS/p12.png",
            "Viral Luke",
            "Designer"))
        techCommitee.add(TeamModel("https://i.ibb.co/qsRj2yg/p20.png",
            "Saksham",
            "Developer"))
        techCommitee.add(TeamModel("https://i.ibb.co/LvKc02H/p23.png",
            "Rajat Verma",
            "Developer"))
        techCommitee.add(TeamModel("https://i.ibb.co/NpVrvsx/p21.png",
            "Pranav Negi",
            "Designer "))

        val managementAdapter: TeamAdapter = TeamAdapter(managementCommitee)
        val coreTeamAdapter: TeamAdapter = TeamAdapter(coreCommitee)
        val techTeamAdapter: TeamAdapter = TeamAdapter(techCommitee)

        recyclerViewManagementCommitee.adapter = managementAdapter
        recyclerCoreTeamCommitee.adapter = coreTeamAdapter
        recyclerTechTeamCommitee.adapter = techTeamAdapter

    }


}
