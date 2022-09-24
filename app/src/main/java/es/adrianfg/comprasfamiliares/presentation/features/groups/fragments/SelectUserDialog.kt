package es.adrianfg.comprasfamiliares.presentation.features.groups.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.CreateGroupViewModel

class SelectUserDialog(val viewModel: CreateGroupViewModel) :DialogFragment() {
    val listUsers = viewModel.userList.value ?: emptyList()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>()
            val builder = AlertDialog.Builder(it)
            val emailsArray = getEmails(listUsers)

            builder.setTitle(R.string.pick_users)
                .setMultiChoiceItems(emailsArray, null,
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            selectedItems.add(which)
                        } else if (selectedItems.contains(which)) {
                            selectedItems.remove(which)
                        }
                    })
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.selectedUserList.value = getSelectedUsers(selectedItems)
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    //Se le pasa la lista de seleccionados de tipo int y devuelve una lista de emails de tipo String
    private fun getSelectedUsers(selectedItems: ArrayList<Int>): List<String> {
        val selectedUsers = mutableListOf<String>()
        if (!selectedItems.isEmpty()) {
            for (indice in selectedItems) {
                selectedUsers.add(listUsers.elementAt(indice).email)
            }
        }
        return selectedUsers.toList()
    }

    //Se le pasa una lista de usuarios y devuelve una lista de emails
    private fun getEmails(listUsers: List<User>): Array<String> {
        val arrayList = arrayListOf<String>()
        for (user in listUsers) {
            arrayList.add(user.email)
        }
        return arrayList.toTypedArray()
    }

}