package tronku.project.zealicon.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_footer.*
import kotlinx.android.synthetic.main.menu_footer.view.*
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import tronku.project.zealicon.Adapter.DuoMenuAdapter
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.Status
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.ExtraUtils
import tronku.project.zealicon.Viewmodel.MainViewModel
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), DuoMenuView.OnMenuClickListener {

    private lateinit var navController: NavController
    private lateinit var duoAdapter: DuoMenuAdapter
    private val viewModel by lazy { MainViewModel() }
    private val db by lazy { RoomDB(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setNavButtons()
        handleMenu()
        fetchData()
        setObservers()
    }

    private fun fetchData() {
        if (ExtraUtils.isConnected(this)) {
            viewModel.loadData().observe(this, Observer { res ->
                when (res.status) {
                    Status.LOADING -> {
                        loaderLayout.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        loaderLayout.visibility = View.GONE
                        Log.e("ERROR", res.msg.toString())
                        checkForLocalData()
                    }
                    Status.SUCCESS -> viewModel.parse(db, res.data.toString())
                }
            })
        }
    }

    private fun checkForLocalData() {
        viewModel.hasLocalData.observe(this, Observer {
            errorLayout.visibility = if (it) View.GONE else View.VISIBLE
            if (it)
                Toast.makeText(this, "Showing saved data...", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setObservers() {

        viewModel.isParsed.observe(this, Observer {
            if (!it) {
                if (ExtraUtils.isConnected(this@MainActivity))
//                    errorLayout.visibility = View.VISIBLE
//                    Toast.makeText(this@MainActivity, "Error in parsing data...", Toast.LENGTH_SHORT).show()
                loaderLayout.visibility = View.GONE
            } else {
                loaderLayout.visibility = View.GONE
                bottomNavigation.visibility = View.VISIBLE
            }
        })
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
        bottomNavigation.setupWithNavController(
            navController)
    }

    private fun handleMenu() {
        val menuOptions: ArrayList<String> = ArrayList()
        menuOptions.add("Home")
        menuOptions.add("Reach us")
//        menuOptions.add("Sponsors")
        menuOptions.add("Team")
        menuOptions.add("About")

        duoAdapter = DuoMenuAdapter(menuOptions)
        duoMenuView.setOnMenuClickListener(this)
        duoMenuView.adapter = duoAdapter

        duoAdapter.setViewSelected(0, true)
    }



    private fun setNavButtons() {

        navDrawerIcon.setOnClickListener {
            duoDrawerLayout.openDrawer()
            ExtraUtils.hideKeyboard(this)
        }

        buttonFacebook.setOnClickListener {
            try {
                applicationContext.packageManager.getPackageInfo("com.facebook.katana", 0)
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
                applicationContext.packageManager.getPackageInfo("com.instagram.android", 0)
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

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {

        when (navController.currentDestination?.label){
            "fragment_about" -> navController.navigate(R.id.action_fragmentAbout_to_home)
            "fragment_team" -> navController.navigate(R.id.action_fragmentTeam_to_home)
            "fragment_sponsor" -> navController.navigate(R.id.action_fragmentSponsor_to_home)
            "fragment_route" -> navController.navigate(R.id.action_fragmentRoute_to_home)
            "home_fragment" -> {
                val snackbar = Snackbar.make(duoDrawerLayout, "Do you want to exit?", Snackbar.LENGTH_SHORT)
                snackbar.setAction("Yes", View.OnClickListener {
                    finishAffinity()
                }).show()
            }
            else -> super.onBackPressed()
        }
        bottomNavigation.visibility = View.VISIBLE
        duoAdapter.setViewSelected(0, true)
    }



    override fun onOptionClicked(position: Int, objectClicked: Any?) {
        duoDrawerLayout.closeDrawer()

        when (position){
            0 -> {
                navController.navigate(R.id.home)
                bottomNavigation.visibility = View.VISIBLE
                duoAdapter.setViewSelected(0, true)
            }
            1 -> {
                navController.navigate(R.id.fragmentRoute)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(1, true)
            }
            10 -> {
                navController.navigate(R.id.fragmentSponsor)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(2, true)
            }
            2 -> {
                navController.navigate(R.id.fragmentTeam)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(2, true)
            }
            3 -> {
                navController.navigate(R.id.fragmentAbout)
                bottomNavigation.visibility = View.GONE
                duoAdapter.setViewSelected(3, true)
            }

        }

    }

    override fun onHeaderClicked() {}

    override fun onFooterClicked() {}

}
