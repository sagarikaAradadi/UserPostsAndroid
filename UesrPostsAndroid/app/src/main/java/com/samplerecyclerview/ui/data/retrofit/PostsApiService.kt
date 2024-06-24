package com.samplerecyclerview.ui.data.retrofit

import com.samplerecyclerview.ui.ui.model.UsersModel
import retrofit2.Response
import retrofit2.http.GET

interface PostsApiService {

    @GET("users")
    suspend fun userApi(
    ): Response<ArrayList<UsersModel>?>
}