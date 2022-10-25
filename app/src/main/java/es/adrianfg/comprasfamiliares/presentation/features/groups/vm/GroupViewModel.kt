package es.adrianfg.comprasfamiliares.presentation.features.groups.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.usecase.DeleteAllProductsUseCase
import es.adrianfg.comprasfamiliares.domain.usecase.DeleteGroupUseCase
import es.adrianfg.comprasfamiliares.domain.usecase.GetListGroupsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getListGroupsUseCase: GetListGroupsUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val deleteAllProductsUseCase: DeleteAllProductsUseCase,

) : BaseViewModel() {
    private val _groupList = MutableLiveData<List<Group>>()
    val groupList: LiveData<List<Group>> get() = _groupList

    fun loadGroupsList(user: User) {
        viewModelScope.launch {
            getListGroupsUseCase.execute(
                GetListGroupsUseCase.Params(user)
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _groupList.value = it }
        }
    }

    fun deleteGroup(group: Group) {
        viewModelScope.launch {
            deleteGroupUseCase.execute(
                DeleteGroupUseCase.Params(group)
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _groupList.value = it }
        }
    }

    fun buyAllProduct(group: Group) {
        viewModelScope.launch {
            deleteAllProductsUseCase.execute(
                DeleteAllProductsUseCase.Params(group)
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { }
        }
    }


}