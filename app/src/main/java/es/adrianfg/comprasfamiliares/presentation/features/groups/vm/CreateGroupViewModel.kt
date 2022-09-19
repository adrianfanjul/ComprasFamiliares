package es.adrianfg.comprasfamiliares.presentation.features.groups.vm


import android.content.Context
import android.net.Uri
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.BuildConfig
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
import java.io.File
import javax.inject.Inject


@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val setGroupsUseCase: SetGroupsUseCase,
    private val getListUsersUseCase: GetListUsersUseCase,
) : BaseViewModel() {

    lateinit var imageView:AppCompatImageView
    private val minlengh = 3
    val description = MutableLiveData("")
    val name = MutableLiveData("")
    val image = MutableLiveData("")
    private val _selectedUserList = MutableLiveData<List<String>>()
    val selectedUserList get() = _selectedUserList

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList
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
    val enableBtn: LiveData<Boolean> = name.combine(description) { name, description ->
        return@combine name.length > minlengh && description.length > minlengh
    }

    private val _group = MutableLiveData<Group>()
    val group get() = _group

    fun create() {
        image.value="Grupos/${name.value}.jpg"
        viewModelScope.launch {
            setGroupsUseCase.execute(
                SetGroupsUseCase.Params(
                    Group(
                        name.value ?: "",
                        description.value ?: "",
                        image.value ?: "",
                        selectedUserList.value?:emptyList()
                    )
                )
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect {
                    _group.value = it
                    imageView.uploadImage("Grupos/${it.name}.jpg")
                }
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

    fun getTmpFileUri(context: Context): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".jpg", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

}