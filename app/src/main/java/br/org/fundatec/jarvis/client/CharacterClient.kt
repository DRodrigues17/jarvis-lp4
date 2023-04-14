package br.org.fundatec.jarvis.client


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface CharacterClient {
    @GET("/api/character/{userId}")
    suspend fun getCharacters(@Query("userId") userId: Int): List<Character>

    @POST("/api/character/{userId}")
    suspend fun createCharacter(@Query("userId") userId: Int, @Body character: Character): Character
}

data class Character(
    val name: String,
    val description: String,
    val imageLink: String,
    val universeType: String,
    val characterType: String,
    val age: Int,
    val date: Date?
)