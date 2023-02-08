package es.adrianfg.comprasfamiliares.presentation.features.login.vm

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.combine
import es.adrianfg.comprasfamiliares.core.extension.get_SHA_512_SecurePassword
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
    private val minCharacters = 8
    val userName = MutableLiveData("")

    val errorUser = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && userName.value?.isEmail() == false
            }
        )
    }

    val errorPassword = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && password.value?.isValidPass() == false
            }
        )
    }

    val password = MutableLiveData("")

    val enableBtn: LiveData<Boolean> = userName.combine(password) { user, pass ->
        return@combine user.length >= minCharacters && pass.length >=minCharacters
    }

    private val _user = MutableLiveData<User>()
    val user get() = _user

    fun logIn() {

        val codePass = get_SHA_512_SecurePassword(password.value ?: "", userName.value ?: "")
        viewModelScope.launch {
            getLogInUseCase.execute(
                GetLogInUseCase.Params(
                    userName.value ?: "",
                    codePass
                )
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _user.value = it }
        }
    }

}