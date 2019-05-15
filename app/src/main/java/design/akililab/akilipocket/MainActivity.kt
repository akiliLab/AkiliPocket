package design.akililab.akilipocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import design.akililab.akilipocket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment? ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)
    }


    private fun setupBottomNavMenu(navController: NavController) {

            val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNav?.setupWithNavController(navController)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item!!.onNavDestinationSelected(findNavController(R.id.homeNavHostFragment)) || super.onOptionsItemSelected(item)
    }





}
