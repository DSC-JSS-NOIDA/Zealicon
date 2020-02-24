package tronku.project.zealicon.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_route.*
import kotlinx.android.synthetic.main.fragment_route.buttonFacebook
import kotlinx.android.synthetic.main.fragment_route.buttonWebsie
import kotlinx.android.synthetic.main.menu_footer.*
import tronku.project.zealicon.Adapter.RouteAdapter
import tronku.project.zealicon.Model.ContactMemberModel
import tronku.project.zealicon.R
import tronku.project.zealicon.Viewmodel.MyPlaylistViewModel
import kotlinx.android.synthetic.main.fragment_route.buttonInstagram as buttonInstagram1


class RouteFragment : Fragment() {

    companion object {
        fun newInstance() = RouteFragment()
    }

    private lateinit var viewModel: MyPlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_route, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyPlaylistViewModel::class.java)
        // TODO: Use the ViewModel

        handleClicks()

        val contactMembers: ArrayList<ContactMemberModel> = ArrayList()
        contactMembers.add(ContactMemberModel(R.drawable.m2, "Dr. Prashant Chauhan", "Festival Convener", "email", "zealicon@jssaaten.ac.in"))
        contactMembers.add(ContactMemberModel(R.drawable.c1, "Kushagra Bharadwaj", "Festival Secretary", "mobile", "9411224086"))
        contactMembers.add(ContactMemberModel(R.drawable.c2, "Devanshu Monga", "Festival Secretary", "mobile", "9899863709"))

        val routeAdapter: RouteAdapter = RouteAdapter(contactMembers)
        routeRecyclerView.adapter = routeAdapter


    }

    private fun handleClicks() {

        routeMap.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:26.613390,77.360402?q=JSS Academy Of Technical Education")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        buttonFacebook.setOnClickListener {
            try {
                context?.packageManager?.getPackageInfo("com.facebook.katana", 0)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/zealicon")))
            } catch (e: Exception) {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/zealicon")
                ))
            }
        }

        buttonInstagram.setOnClickListener {
            try {
                context?.packageManager?.getPackageInfo("com.instagram.android", 0)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/zealicon")))
            } catch (e: Exception) {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/zealicon")
                ))
            }
        }

        buttonWebsie.setOnClickListener {
            startActivity(Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.zealicon.in")
            ))
        }

    }


}
