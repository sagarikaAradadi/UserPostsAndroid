package com.samplerecyclerview.ui.data.repository

import com.samplerecyclerview.ui.data.retrofit.PostsApiService
import javax.inject.Inject

class UsersRepository @Inject constructor(private val apiService: PostsApiService) {
    suspend fun getUsers() = apiService.userApi()
}