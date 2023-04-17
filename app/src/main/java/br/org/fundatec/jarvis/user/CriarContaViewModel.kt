package br.org.fundatec.jarvis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.org.fundatec.jarvis.sealed.ContaViewState

class CriarContaViewModel : ViewModel() {


    private val state = MutableLiveData<ContaViewState>()
    val viewState: LiveData<ContaViewState> = state

    fun validarInsersoesUsuario(
        nome: String?, email: String?, senha: String?
    ) {
        if (nome.isNullOrEmpty() || email.isNullOrEmpty() || senha.isNullOrEmpty()) {
            state.value = ContaViewState.MostrarErroCamposNulos
        } else {
            state.value = ContaViewState.MostrarCasoDeSucesso
        }
    }

}

