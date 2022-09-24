package es.adrianfg.comprasfamiliares.presentation.features.login.vm

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.*
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.usecase.SetRegisterUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val setRegisterUseCase: SetRegisterUseCase,
) : BaseViewModel() {
    private val minCharacters = 3
    val email = MutableLiveData("")
    val pass = MutableLiveData("")
    val name = MutableLiveData("")
    val surName = MutableLiveData("")
    val age = MutableLiveData("")
    val errorEmail = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && email.value?.isEmail() == false
            }
        )
    }
    val errorPass = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && pass.value?.isValidPass() == false
            }
        )
    }
    val errorName = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && name.value?.isValidName() == false
            }
        )
    }
    val enableBtn: LiveData<Boolean> = email.combine(pass, name) { user, pass, name ->
        return@combine user.length > minCharacters && pass.length > minCharacters && name.length > minCharacters
    }

    private val _user = MutableLiveData<User>()
    val user get() = _user

    fun register() {
        val codePass = get_SHA_512_SecurePassword(pass.value ?: "", email.value ?: "")
        viewModelScope.launch {
            setRegisterUseCase.execute(
                SetRegisterUseCase.Params(
                    User(
                        email.value ?: "",
                        codePass ?: "",
                        name.value ?: "",
                        surName.value ?: "",
                        age.value?.toIntOrNull() ?: -1
                    )
                )
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _user.value = it }
        }
    }
}