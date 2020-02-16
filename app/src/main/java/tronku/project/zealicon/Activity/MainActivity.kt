package tronku.project.zealicon.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_footer.*
import kotlinx.android.synthetic.main.menu_footer.view.*
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import tronku.project.zealicon.R


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var duoDrawerLayout: DuoDrawerLayout
    lateinit var duoMenuView: DuoMenuView
    lateinit var menuAdapter: MenuAdapter
    lateinit var menuOptions: ArrayList<String>
    lateinit var mToolbar: androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        setupNavigation()
        setNavButtons()
        navDrawerIcon.setOnClickListener {
            duoDrawerLayout.openDrawer()
        }
    }


    private fun initializeViews() {
        duoDrawerLayout = findViewById(R.id.duoDrawerLayout)
        duoMenuView = findViewById(R.id.duoMenuView)
        mToolbar = findViewById(R.id.toolbar)
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
        bottomNavigation.setupWithNavController(
            navController)
    }


    private fun setNavButtons() {

        duoMenuView.buttonFacebook.setOnClickListener {
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

        duoMenuView.buttonInstagram.setOnClickListener {
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


        duoMenuView.buttonWebsie.setOnClickListener {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.zealicon.in")
                )
        }


    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}
