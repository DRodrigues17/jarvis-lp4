package br.org.fundatec.jarvis.character.data.remote

import android.util.Log
import br.org.fundatec.jarvis.character.data.api.CharacterClient
import br.org.fundatec.jarvis.character.domain.model.CharacterErrorModel
import br.org.fundatec.jarvis.data.Character
import br.org.fundatec.jarvis.webservice.RetrofitNetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import br.org.fundatec.jarvis.webservice.Result

class CharacterRemoteDataSource {

    private val service =
        RetrofitNetworkClient.createNetworkClient().create(CharacterClient::class.java)

    suspend fun getCharacters(userId: Int): Result<List<Character>, CharacterErrorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getCharacters(userId)
                if (response.isSuccessful) {
                    Result.Success(response.body().orEmpty())
                } else  {
                    Result.Error(CharacterErrorModel.ErrorAoBuscarLista)
                }
            } catch (ex: Exception) {
                Log.e("erro ao pegar personagens ", ex.message ?: "")
                Result.Error(CharacterErrorModel.ErrorAoBuscarLista)
            }

        }
    }

    suspend fun createCharacter(userId: Int, character: Character): Result<Unit, CharacterErrorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.createCharacter(userId, character)
                if (response.isSuccessful) {
                    Result.Success(Unit)
                } else {
                    Result.Error(CharacterErrorModel.ErrorAoSalvar)
                }
            } catch (ex: Exception) {
                Log.e("erro ao criar peronagem ", ex.message ?: "")
                Result.Error(CharacterErrorModel.ErrorAoSalvar)
            }
        }
    }

    suspend fun deleteCharacter(characterid: Int): Result<Unit, CharacterErrorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.deleteCharacter(characterid)
                if (response.isSuccessful) {
                    Result.Success(Unit)
                } else {
                    Result.Error(CharacterErrorModel.ErrorAoRemover)
                }
            } catch (ex: Exception) {
                Log.e("erro ao pegar personagens ", ex.message ?: "")
                Result.Error(CharacterErrorModel.ErrorAoRemover)
            }
        }
    }

}