package tronku.project.zealicon.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.bvapp.arcmenulibrary.ArcMenu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import tronku.project.zealicon.R

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
        bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    val ITEM_DRAWABLES = listOf( R.color.red_100,
        R.color.green_100,
        R.color.red_100,
        R.color.green_100,
        R.color.red_100,
        R.color.green_100)

    val STR = listOf("Home",
        "Route",
        "Connect",
        "Sponsor",
        "Team",
        "About")

  /*  var arcMenu: ArcMenu =  findViewById(R.id.arcMenu)
    arcMenu.showTooltip=true
    menu.setToolTipBackColor(Color.WHITE);
    menu.setToolTipCorner(6f);
    menu.setToolTipPadding(2f);
    menu.setToolTipTextColor(Color.BLUE);
    menu.setAnim(300,300,ArcMenu.ANIM_MIDDLE_TO_RIGHT,ArcMenu.ANIM_MIDDLE_TO_RIGHT,
    ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE,ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE);

    final int itemCount = ITEM_DRAWABLES.length;
    for (int i = 0; i < itemCount; i++) {
        FloatingActionButton item = new FloatingActionButton(this);

        item.setSize(FloatingActionButton.SIZE_MINI); // set initial size for child, it will create fab first
        item.setIcon(itemDrawables[i]); // It will set fab icon from your resources which related to 'ITEM_DRAWABLES'
        item.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // it will set fab child's color
        menu.setChildSize(item.getIntrinsicHeight()); // set absolout child size for menu

        val position: Int = i;
        menu.addItem(item, str[i], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //You can access child click in here
            }
        });
    }

   */
}
