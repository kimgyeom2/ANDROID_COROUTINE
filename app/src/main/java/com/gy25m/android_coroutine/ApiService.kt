package com.gy25m.android_coroutine

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("token")
    fun getToken(
        @Field("grant_type") grant_type: String = "client_credentials",
        @Field("client_id") client_id: String = ""
    ): Single<TokenResponse>



}

data class TokenResponse(
    val access_token: String,
    val expires_in: String,
    val token_type: String
)