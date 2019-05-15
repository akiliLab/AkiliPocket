package design.akililab.akilipocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import design.akililab.akilipocket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    


}
