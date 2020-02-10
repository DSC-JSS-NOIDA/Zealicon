package tronku.project.zealicon.Activity

import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
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
        bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}
