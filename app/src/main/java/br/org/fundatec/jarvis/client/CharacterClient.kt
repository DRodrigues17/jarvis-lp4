package br.org.fundatec.jarvis.client


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*
import br.org.fundatec.jarvis.data.Character as Character

interface CharacterClient {
    @GET("character/{userId}")
    suspend fun getCharacters(@Path("userId") userId: Int): List<Character>

    @POST("character/{userId}")
    suspend fun createCharacter(@Path("userId") userId: Int, @Body character: Character): Character
}

