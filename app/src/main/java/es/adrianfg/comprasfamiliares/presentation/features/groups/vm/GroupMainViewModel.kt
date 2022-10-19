package es.adrianfg.comprasfamiliares.presentation.features.groups.vm

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.domain.models.User
import javax.inject.Inject

@HiltViewModel
class GroupMainViewModel @Inject constructor() : BaseViewModel() {
    private val _user = MutableLiveData<User>()
    val user get() = _user

    fun setUser(user: User) {
        _user.value = user
    }

}