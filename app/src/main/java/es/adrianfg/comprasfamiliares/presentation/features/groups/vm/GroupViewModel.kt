package es.adrianfg.comprasfamiliares.presentation.features.groups.vm

import android.os.Looper
import android.view.View
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.gone
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.usecase.GetListGroupsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getListGroupsUseCase: GetListGroupsUseCase
) : BaseViewModel() {

    private val _groupList = MutableLiveData<List<Group>>()
    val groupList: LiveData<List<Group>> get() = _groupList
    val logedUser = MutableLiveData("")

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

    fun reloadList(user: User) {
        val myHandler = android.os.Handler(Looper.getMainLooper())
        myHandler.post(object : Runnable {
            override fun run() {
                loadGroupsList(user)
                myHandler.postDelayed(this, 5000 /*5 segundos*/)
            }
        })
    }

    fun showDeleteButton(logedUser: String, groupsRv: RecyclerView) {

    }

}