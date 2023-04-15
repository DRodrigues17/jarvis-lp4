package br.org.fundatec.jarvis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CriarContaViewModel : ViewModel() {


    private val state = MutableLiveData<ViewContaState>()
    val viewState: LiveData<ViewContaState> = state

    fun validarInsersoesUsuario(
        nome: String?, email: String?, senha: String?
    ) {
        if (nome.isNullOrEmpty() || email.isNullOrEmpty() || senha.isNullOrEmpty()) {
            state.value = ViewContaState.MostrarErroCamposNulos
        } else {
            state.value = ViewContaState.MostrarCasoDeSucesso
        }
    }

}

sealed class ViewContaState {
    object MostrarErroCamposNulos : ViewContaState()
    object MostrarCasoDeSucesso : ViewContaState()
}