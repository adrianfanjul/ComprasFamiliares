package es.adrianfg.comprasfamiliares.presentation.features.login.vm

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.combine
import es.adrianfg.comprasfamiliares.core.extension.isEmail
import es.adrianfg.comprasfamiliares.core.extension.isValidPass
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.usecase.GetLogInUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLogInUseCase: GetLogInUseCase,
) : BaseViewModel() {

    val userName = MutableLiveData("")
    val errorUser = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && userName.value?.isEmail() == false
            }
        )
    }
    val password = MutableLiveData("")
    val enableBtn: LiveData<Boolean> = userName.combine(password) { user, pass ->
        return@combine user.length > 3 && pass.isValidPass()
    }

    private val _user = MutableLiveData<User>()
    val user get() = _user

    fun logIn() {
        viewModelScope.launch {
            getLogInUseCase.execute(
                GetLogInUseCase.Params(
                    userName.value ?: "",
                    password.value ?: ""
                )
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _user.value= it }
        }
    }


}