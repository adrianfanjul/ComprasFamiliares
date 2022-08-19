package es.adrianfg.comprasfamiliares.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.core.extension.viewBinding
import es.adrianfg.comprasfamiliares.data.response.UserResponse
import es.adrianfg.comprasfamiliares.databinding.ActivityLoginBinding


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by viewBinding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment=supportFragmentManager.findFragmentById(binding.navHostLoginContent.id) as NavHostFragment

        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //createUser()

    }

    /*
    private fun createUser() {
        // Write a message to the database
        val FIREBASE = "https://travelling-ddf68-default-rtdb.europe-west1.firebasedatabase.app/"
        val database: FirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE)
        val userResponse = UserResponse("adrian@gmail.es","123","adrian", "fanjul", 33)
        database.getReference("Users").push().setValue(userResponse)
    }
*/
    /*
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_login_content)
        return (navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }
*/
}