package br.org.fundatec.jarvis.character.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateCharacterViewModel : ViewModel() {
    private val state = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = state

    fun validarInputs(
        nome: String?,
        descricao: String?,
        idade: String?,
        data: String?,
        link: String?
    ) {
        if (nome.toString().isEmpty() || descricao.toString().isEmpty() || idade.toString()
                .isEmpty()
            || data.toString().isEmpty() || link.toString().isEmpty()
        ) {
            state.value = ViewState.ShowError
        } else {
            state.value = ViewState.ShowSuccess
        }
    }

    sealed class ViewState {
        object ShowError : ViewState()
        object ShowSuccess : ViewState()
    }
}