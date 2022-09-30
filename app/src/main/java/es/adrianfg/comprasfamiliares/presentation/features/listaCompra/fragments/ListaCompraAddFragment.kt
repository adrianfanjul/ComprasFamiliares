package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentListaCompraAddBinding
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraAddViewModel
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraMainViewModel


@AndroidEntryPoint
class ListaCompraAddFragment : BaseFragmentDb<FragmentListaCompraAddBinding, ListaCompraAddViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_lista_compra_add
    override val viewModel: ListaCompraAddViewModel by viewModels()
    val sharedViewModel: ListaCompraMainViewModel by activityViewModels ()
    private var latestTmpUri: Uri? = null
    private val galeryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            dataBinding.listaCompraAddImage.setImageURI(intent?.data)
        }
    }
    private val takePhotoActivityLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                dataBinding.listaCompraAddImage.setImageURI(uri)
            }
        }
    }
    private val requestSinglePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            lifecycleScope.launchWhenStarted {
                viewModel.getTmpFile(requireContext()).let { uri ->
                    latestTmpUri = uri
                    takePhotoActivityLauncher.launch(uri)
                }
            }
        } else {
            snack(SnackbarMessage(R.string.camera_denegate)).show()
        }
    }

    override fun initViewModels() {
        viewModel.imageView = dataBinding.listaCompraAddImage
        viewModel.user.value = sharedViewModel.user.value?.email
        viewModel.group.value = sharedViewModel.group.value?.name
    }

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }

    override fun eventListeners() {
        dataBinding.listaCompraAddExitBtn.setOnClickListener { exit() }
        dataBinding.listaCompraAddChooseBtn.setOnClickListener { openGallery() }
        dataBinding.listaCompraAddCaptureBtn.setOnClickListener { openCamera() }
    }

    override fun observeViewModels() {
        viewModel.errorName.observe(viewLifecycleOwner, ::errorName)
        viewModel.product.observe(viewLifecycleOwner, ::createSucess)
    }

    override fun showError(message: String?) {
        snack(SnackbarMessage(R.string.error_create_product, varargs = message)).show()
    }

    private fun exit() {
        val directions = ListaCompraAddFragmentDirections.listaCompraAddFragmentToListaCompraFragment()
        navigate(directions)
    }

    private fun createSucess(product: Product?) {
        snack(SnackbarMessage(R.string.products_create, varargs = product?.name)).show()
        exit()
    }

    private fun errorName(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.listaCompraAddInputLayoutName.error =
                    getString(R.string.register_error_name)
                false -> dataBinding.listaCompraAddInputLayoutName.error = null
            }
        }

    }

    private fun openGallery() {
        galeryActivityLauncher.launch(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
    }

    private fun openCamera() {
        requestSinglePermission.launch(Manifest.permission.CAMERA)
    }

}

