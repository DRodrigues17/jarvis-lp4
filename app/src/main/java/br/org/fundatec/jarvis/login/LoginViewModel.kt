package br.org.fundatec.jarvis.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val state = MutableLiveData<ViewLoginState>()
    val viewState: LiveData<ViewLoginState> = state

    fun validarInsersoesUsuario(
        email: String?, senha: String?
    ) {
        if (email.isNullOrEmpty() || senha.isNullOrEmpty()) {
            state.value = ViewLoginState.MostrarErroCamposNulos
        } else {
            state.value = ViewLoginState.MostrarCasoDeSucesso
        }
    }

}

sealed class ViewLoginState {
    object MostrarErroCamposNulos : ViewLoginState()
    object MostrarCasoDeSucesso : ViewLoginState()
}