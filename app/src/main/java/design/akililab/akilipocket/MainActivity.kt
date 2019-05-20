package design.akililab.akilipocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import design.akililab.akilipocket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


   private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        navController = findNavController(R.id.homeNavHostFragment)


        binding.bottomNavigation.setupWithNavController(navController)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return item!!.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


}
