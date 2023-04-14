package br.org.fundatec.jarvis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CriarContaViewModel : ViewModel() {

    private val regex: Regex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    private val state = MutableLiveData<ViewContaState>()
    val viewState: LiveData<ViewContaState> = state

    fun validarInsersoesUsuario(
        nome: String?, email: String?, senha: String?
    ) {
        if (nome.isNullOrEmpty() || email.isNullOrEmpty() || senha.isNullOrEmpty()) {
            state.value = ViewContaState.MostrarErroCamposNulos
        } else if (!email.matches(regex)) {
            state.value = ViewContaState.MostrarErroEmailInvalido
        } else {
            state.value = ViewContaState.MostrarCasoDeSucesso
        }
    }

}

sealed class ViewContaState {
    object MostrarErroEmailInvalido : ViewContaState()
    object MostrarErroCamposNulos : ViewContaState()
    object MostrarCasoDeSucesso : ViewContaState()
}