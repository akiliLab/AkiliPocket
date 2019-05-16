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


    lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        setSupportActionBar(binding.myToolbar)

        // Remove title name

        supportActionBar!!.setDisplayShowTitleEnabled(false)


        navController = findNavController(R.id.homeNavHostFragment)

        binding.bottomNavigation.setupWithNavController(navController)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item!!.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }



}
