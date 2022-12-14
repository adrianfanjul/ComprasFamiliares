package es.adrianfg.comprasfamiliares.presentation.features.groups.vm


import android.content.Context
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.*
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.usecase.GetListUsersUseCase
import es.adrianfg.comprasfamiliares.domain.usecase.SetGroupsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val setGroupsUseCase: SetGroupsUseCase,
    private val getListUsersUseCase: GetListUsersUseCase

) : BaseViewModel() {

    lateinit var imageView: AppCompatImageView
    private val minlengh = 3
    val description = MutableLiveData("")
    val name = MutableLiveData("")
    val image = MutableLiveData("")
    private val _selectedUserList = MutableLiveData<List<String>>()
    val selectedUserList get() = _selectedUserList
    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList
    val createUser = MutableLiveData("")

    val errorName = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && name.value?.isValidName() == false
            }
        )
    }

    val errorDescription = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && description.value?.isValidDescription() == false
            }
        )
    }

    val enableBtn: LiveData<Boolean> = name.combine(description,selectedUserList) { name, description,selectedUserList ->
        return@combine name.length > minlengh && description.length > minlengh && selectedUserList.isNotEmpty()
    }

    private val _group = MutableLiveData<Group>()
    val group get() = _group

    fun create() {
        image.value = getImageRoute()
        viewModelScope.launch {
            setGroupsUseCase.execute(
                SetGroupsUseCase.Params(
                    Group(
                        name.value ?: "",
                        description.value ?: "",
                        image.value ?: "",
                        selectedUserList.value ?: emptyList(),
                        createUser.value ?: ""
                    ),imageView
                )
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect {_group.value = it }
        }
    }

    fun loadUsersList() {
        viewModelScope.launch {
            getListUsersUseCase.execute(Unit)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _userList.value = it }
        }
    }

    private fun getImageRoute():String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        return "Groups/${name.value}${formatted}.jpg"
    }

    fun getTmpFile(context: Context): Uri {
        return getTmpFileUri(context)
    }

}