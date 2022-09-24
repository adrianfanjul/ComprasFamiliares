package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import javax.inject.Inject

@HiltViewModel
class ListaCompraMainViewModel @Inject constructor() : BaseViewModel() {
    private val _user = MutableLiveData<User>()
    val user get() = _user

    fun setUser(user: User) {
        _user.value = user
    }

    private val _group = MutableLiveData<Group>()
    val group get() = _group

    fun setGroup(group: Group) {
        _group.value = group
    }
}