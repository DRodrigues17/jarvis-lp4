package br.org.fundatec.jarvis.client

import br.org.fundatec.jarvis.data.UserRequest
import br.org.fundatec.jarvis.data.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface UserClient {

    @GET("login")
    suspend fun getUser(
        @Query("password") password: String,
        @Query("email") email: String
    ): UserResponse

    @POST("login")
    suspend fun createUser(@Body userRequest: UserRequest): UserRequest
}