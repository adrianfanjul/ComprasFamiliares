package es.adrianfg.comprasfamiliares.presentation.features.groups.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.extension.viewBinding
import es.adrianfg.comprasfamiliares.databinding.ActivityGroupBinding
import es.adrianfg.comprasfamiliares.databinding.ActivityLoginBinding

@AndroidEntryPoint
class GroupActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by viewBinding(ActivityGroupBinding::inflate)

    val args: GroupActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment=supportFragmentManager.findFragmentById(binding.navHostGroupContent.id) as NavHostFragment

        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = getString(R.string.group_toolbar_title,args.user?.name)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_group_content)
        return (navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }

}