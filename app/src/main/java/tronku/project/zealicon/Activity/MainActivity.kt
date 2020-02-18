package tronku.project.zealicon.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.appcompat.view.menu.MenuBuilder
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_footer.*
import kotlinx.android.synthetic.main.menu_footer.view.*
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import tronku.project.zealicon.DuoMenuAdapter
import tronku.project.zealicon.Fragment.*
import tronku.project.zealicon.R
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), DuoMenuView.OnMenuClickListener {

    private lateinit var navController: NavController
    private lateinit var duoAdapter: DuoMenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setNavButtons()
        handleMenu()
        navDrawerIcon.setOnClickListener {
            duoDrawerLayout.openDrawer()
        }
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
        bottomNavigation.setupWithNavController(
            navController)
    }

    private fun handleMenu() {
        val menuOptions: ArrayList<String> = ArrayList()
        menuOptions.add("Home")
        menuOptions.add("Route")
        menuOptions.add("Sponsors")
        menuOptions.add("Team")
        menuOptions.add("About")

        duoAdapter = DuoMenuAdapter(menuOptions)
        duoMenuView.setOnMenuClickListener(this)
        duoMenuView.adapter = duoAdapter

        duoAdapter.setViewSelected(0, true)
    }



    private fun setNavButtons() {

        this.buttonFacebook.setOnClickListener {
            try {
                applicationContext.getPackageManager().getPackageInfo("com.facebook.katana", 0)
                Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/zealicon"))
            } catch (e: Exception) {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/zealicon")
                )
            }
        }

        duoDrawerLayout.buttonInstagram.setOnClickListener {
            try {
                applicationContext.getPackageManager().getPackageInfo("com.instagram.android", 0)
                Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/zealicon"))
            } catch (e: Exception) {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/zealicon")
                )
            }
        }


        buttonWebsie.setOnClickListener {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.zealicon.in")
                )
        }


    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {

        when (navController.currentDestination?.label){
            "fragment_about" -> navController.navigate(R.id.action_fragmentAbout_to_home)
            "fragment_team" -> navController.navigate(R.id.action_fragmentTeam_to_home)
            "fragment_sponsor" -> navController.navigate(R.id.action_fragmentSponsor_to_home)
            "fragment_route" -> navController.navigate(R.id.action_fragmentRoute_to_home)
            else -> super.onBackPressed()
        }
        bottomNavigation.visibility = View.VISIBLE
    }



    override fun onOptionClicked(position: Int, objectClicked: Any?) {
        duoDrawerLayout.closeDrawer()

        when (position){
            0 -> {
//                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.home)
                bottomNavigation.visibility = View.VISIBLE
                duoAdapter.setViewSelected(0, true)
            }
            1 -> {
//                Toast.makeText(this, "Route", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.fragmentRoute)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(1, true)
            }
            2 -> {
//                Toast.makeText(this, "Sponsors", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.fragmentSponsor)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(2, true)
            }
            3 -> {
//                Toast.makeText(this, "Team", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.fragmentTeam)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(3, true)
            }
            4 -> {
//                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.fragmentAbout)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(4, true)
            }

        }

    }

    override fun onHeaderClicked() {
    }

    override fun onFooterClicked() {

    }



}
