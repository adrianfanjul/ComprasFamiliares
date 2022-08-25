package es.adrianfg.comprasfamiliares.presentation.features.groups.vm

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.combine
import es.adrianfg.comprasfamiliares.core.extension.isEmail
import es.adrianfg.comprasfamiliares.core.extension.isValidName
import es.adrianfg.comprasfamiliares.core.extension.isValidPass
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.usecase.SetGroupsUseCase
import es.adrianfg.comprasfamiliares.domain.usecase.SetRegisterUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val setGroupsUseCase: SetGroupsUseCase,
) : BaseViewModel() {
    private val minCharacters = 3
    val description = MutableLiveData("")
    val name = MutableLiveData("")
    val img = MutableLiveData("")

    val errorName = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && name.value?.isValidName() == false
            }
        )
    }
    val enableBtn: LiveData<Boolean> = name.combine(description) { name, description ->
        return@combine name.length > minCharacters && description.length > minCharacters
    }

    private val _group = MutableLiveData<Group>()
    val group get() = _group

    fun create() {
        //TODO Crear la lista de usuarios y el selector de imagen para poder mandarlos
        viewModelScope.launch {
            setGroupsUseCase.execute(
                SetGroupsUseCase.Params(
                    Group(
                        name.value ?: "",
                        description.value ?: "",
                        img.value ?: "" ,
                        emptyList()
                    )
                )
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _group.value = it }
        }
    }
}