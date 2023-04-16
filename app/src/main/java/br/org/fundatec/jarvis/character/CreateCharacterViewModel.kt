package br.org.fundatec.jarvis.character.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.org.fundatec.jarvis.sealed.ViewCharacterState

class CreateCharacterViewModel : ViewModel() {
    private val state = MutableLiveData<ViewCharacterState>()
    val viewState: LiveData<ViewCharacterState> = state

    fun validarInputs(
        nome: String?,
        descricao: String?,
        idade: String?,
        link: String?,
        editora: String?,
        tipo: String?
    ) {
        if (nome.toString().isEmpty() || descricao.toString().isEmpty() || idade.toString()
                .isEmpty() || link.toString().isEmpty() || editora.toString().isEmpty() ||
            tipo.toString().isEmpty()
        ) {
            state.value = ViewCharacterState.MostrarErroCamposNulos
        } else {
            state.value = ViewCharacterState.MostarCasoDeSucesso
        }
    }
}


