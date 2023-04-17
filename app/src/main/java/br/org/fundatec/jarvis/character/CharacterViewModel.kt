package br.org.fundatec.jarvis.character

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.org.fundatec.jarvis.App
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.character.data.remote.CharacterRemoteDataSource
import br.org.fundatec.jarvis.character.domain.model.CharacterType
import br.org.fundatec.jarvis.data.Character
import br.org.fundatec.jarvis.webservice.Result
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    private val state = MutableLiveData<CharacterViewState>()
    val viewState: LiveData<CharacterViewState> = state

    fun init(characterType: Int?) {
        if (characterType == null) {
            Log.e("mensagem de erro", "o tipo de personagem é nulo")
            state.value = CharacterViewState.ShowMessageError(R.string.mensagem_de_erro)
        } else {
            fetchList(CharacterType.values()[characterType])
        }
    }

    private fun fetchList(characterType: CharacterType) {
        state.value = CharacterViewState.ShowLoading
        viewModelScope.launch {
            val userId = App.context.getSharedPreferences("bd", AppCompatActivity.MODE_PRIVATE)
                .getInt("id", 0)
            var result = CharacterRemoteDataSource().getCharacters(userId)
            state.value = when (result) {
                is Result.Success -> {
                    val filteredList = result.value.filter { character ->
                        Log.e("view model 1", characterType.name)
                        Log.e("view model 2", character.characterType)
                        character.characterType == characterType.name
                    }
                    CharacterViewState.ShowContent(filteredList)
                }
                is Result.Error -> CharacterViewState.ShowMessageError(R.string.erro_api)
            }
        }
    }

}

sealed class CharacterViewState {
    object ShowLoading : CharacterViewState()
    data class ShowMessageError(val messageId: Int) : CharacterViewState()
    data class ShowContent(val listaPersonagens: List<Character>) : CharacterViewState()
}