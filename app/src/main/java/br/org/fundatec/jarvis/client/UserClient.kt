package br.org.fundatec.jarvis.client

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface UserClient {

    @GET("api/login")
    suspend fun getUser(
        @Query("password") password: String,
        @Query("email") email: String
    ): UserResponse

    @POST("/api/login")
    suspend fun createUser(@Body userRequest: UserRequest): UserRequest
}

data class UserRequest(
    val name: String,
    val email: String,
    val password: String
)

data class UserResponse(
    val id: Int
)