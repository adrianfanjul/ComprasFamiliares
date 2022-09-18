package es.adrianfg.comprasfamiliares.presentation.features.groups.fragments

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.extension.SelectUserDialog
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentCreateGroupBinding
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.CreateGroupViewModel


@AndroidEntryPoint
class CreateGroupFragment : BaseFragmentDb<FragmentCreateGroupBinding, CreateGroupViewModel>(){

    override fun getLayout(): Int = R.layout.fragment_create_group
    override val viewModel: CreateGroupViewModel by viewModels()
    private var latestTmpUri: Uri? = null
    private val galeryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            dataBinding.createGroupImage.setImageURI(intent?.data)
        }
    }
    private val takePhotoActivityLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                dataBinding.createGroupImage.setImageURI(uri)
            }
        }
    }
    private val requestSinglePermission = registerForActivityResult( ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            lifecycleScope.launchWhenStarted {
                viewModel.getTmpFileUri(requireContext()).let { uri ->
                    latestTmpUri = uri
                    takePhotoActivityLauncher.launch(uri)
                }
            }
        }else{
            snack(SnackbarMessage(R.string.groups_create_camera_denegate)).show()
        }
    }

    override fun initViewModels() {
        viewModel.loadUsersList()
        viewModel.imageView = dataBinding.createGroupImage
    }

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }

    override fun eventListeners() {
        dataBinding.createGroupExitBtn.setOnClickListener { exit() }
        dataBinding.createGroupChooseBtn.setOnClickListener { openGallery() }
        dataBinding.createGroupCaptureBtn.setOnClickListener { openCamera() }
        dataBinding.createGroupUsersAddBtn.setOnClickListener { addUsers() }
    }

    override fun observeViewModels() {
        viewModel.errorName.observe(viewLifecycleOwner, ::errorName)
        viewModel.errorDescription.observe(viewLifecycleOwner, ::errorDescription)
        viewModel.group.observe(viewLifecycleOwner, ::createSucess)
    }

    override fun showError(message: String?) {
        snack(SnackbarMessage(R.string.error_create_group, varargs = message)).show()
    }

    private fun exit() {
        val directions = CreateGroupFragmentDirections.createGroupFragmentToGroupFragment()
        navigate(directions)
    }

    private fun createSucess(group: Group?) {
        snack(SnackbarMessage(R.string.groups_create_group, varargs = group?.name)).show()
        exit()

    }

    private fun errorName(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.createGroupInputLayoutName.error =
                    getString(R.string.register_error_name)
                false -> dataBinding.createGroupInputLayoutName.error = null
            }
        }
    }

    private fun errorDescription(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.createGroupInputLayoutDescription.error =
                    getString(R.string.register_error_description)
                false -> dataBinding.createGroupInputLayoutDescription.error = null
            }
        }
    }

    private fun openGallery() {
        galeryActivityLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
    }

    private fun openCamera() {
        requestSinglePermission.launch(Manifest.permission.CAMERA)
    }

    private fun addUsers() {
        val modalDialog = SelectUserDialog(viewModel.userList.value?: emptyList(),viewModel)
        modalDialog.show(requireActivity().supportFragmentManager, "usersDialog")
    }

}

