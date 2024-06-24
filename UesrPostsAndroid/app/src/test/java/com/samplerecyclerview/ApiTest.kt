package com.samplerecyclerview

import com.samplerecyclerview.ui.data.retrofit.PostsApiService
import com.samplerecyclerview.ui.ui.model.AddressModel
import com.samplerecyclerview.ui.ui.model.UsersModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class ApiTest {
    // Mock your Retrofit service interface (assuming it's UserService)
    private val userService = mock(PostsApiService::class.java)

    // Inject your service into the class where userApi() is defined
    private val userApiService = mock(userService::class.java)

    @Test
    fun `test userApi() success`() {
        // Mock data

        val addressModel = AddressModel("testy", "testuser", "city")
        val expectedPost = UsersModel("testy", "testuser", addressModel)
        val arrayList = java.util.ArrayList<UsersModel>()
        arrayList.add(expectedPost)
        val usersList = arrayListOf(UsersModel("test","testUser",addressModel))

        // Mock the response from Retrofit service
        `when`(runBlocking { userService.userApi() }).thenAnswer {
            Response.success(usersList as ArrayList<UsersModel>)
        }

        // Call the suspending function within runBlocking
        val response = runBlocking { userApiService.userApi() }

        // Assertions
        assertNotNull(response)
        assertEquals(usersList, response.body())
    }

    @Test
    fun `test userApi() failure`() {
        // Mock the failure response from your Retrofit service

        // Call the suspending function within runBlocking
        val response = runBlocking { userApiService.userApi() }

        // Assertions
        assertNotNull(response)
        assertEquals(404, response.code()) // Check the expected error code
    }
}