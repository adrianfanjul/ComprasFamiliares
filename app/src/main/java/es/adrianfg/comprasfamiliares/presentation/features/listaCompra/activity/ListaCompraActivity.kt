package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
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
import es.adrianfg.comprasfamiliares.databinding.ActivityListaCompraBinding
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.fragments.ListaCompraFragmentDirections
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraMainViewModel

@AndroidEntryPoint
class ListaCompraActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by viewBinding(ActivityListaCompraBinding::inflate)
    private val sharedViewModel: ListaCompraMainViewModel by viewModels ()
    private val args: ListaCompraActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment=supportFragmentManager.findFragmentById(binding.navHostListaCompraContent.id) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.toolbar.title = getString(R.string.lista_compra_toolbar_title,args.group?.name)
        sharedViewModel.setUser(args.user ?: User("","","","",-1))
        sharedViewModel.setGroup(args.group ?: Group("","","", emptyList(),""))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_products, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_products_close-> {
                closeSession()
                true
            }
            R.id.menu_products_go_groups-> {
                goGroups()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun closeSession() {
        navController.navigate(R.id.to_loginActivity)
    }

    private fun goGroups() {
        val directions = ListaCompraFragmentDirections.toGroupsActivity(args.user)
        navController.navigate(directions)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_lista_compra_content)
        return (navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }

}