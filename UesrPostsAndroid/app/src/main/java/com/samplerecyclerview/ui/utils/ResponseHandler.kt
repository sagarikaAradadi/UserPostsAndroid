package com.samplerecyclerview.ui.utils

import android.text.TextUtils
import android.util.Log
import com.conduent.nationalhighways.data.model.ErrorResponseModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.util.logging.ErrorManager

object ResponseHandler {

    val VPN_ERROR="100"
    fun <T> success(response: Response<T>?): Resource<T?> {

        Log.e("TAG", "success: response code " + response?.code())
        Log.e("TAG", "success: response isSuccessful " + response?.isSuccessful)

        return if (response?.isSuccessful == true) {

                Resource.Success(response.body())

        } else {
            try {
                val errorResponse =
                    parseError(response)
                return Resource.DataError(errorResponse?.message, errorResponse)
            } catch (e: Exception) {
                Log.e("TAG", "success: message " + e.message)
                return Resource.DataError(e.message)
            }
        }
    }

    private fun <T> parseError(response: Response<T>?): ErrorResponseModel? {
        val gson = Gson()
        val errorBody = response?.errorBody()?.string()

        return try {
            Log.e("TAG", "parseError: message try -> ")
            gson.fromJson(errorBody, ErrorResponseModel::class.java)
                ?: ErrorResponseModel(
                    "",
                    null, "",
                    0
                )
        } catch (e: JsonSyntaxException) {
            Log.e("TAG", "parseError: message -> " + e.message)
            ErrorResponseModel("Error",null,"",0)
        }
    }


    fun <T> failure(e: Exception?): Resource<T?> {
        Log.e("TAG", "failure: e message-> " + e)
        Log.e("TAG", "failure: e message " + e?.message)
         if (e is SocketTimeoutException) {
            Log.e("TAG", "failure: e 22")
            return Resource.DataError(
                "10",
                ErrorResponseModel("Error",null,"",0)

            )
        } else if (e is InterruptedIOException) {
            Log.e("TAG", "failure: e 33")
            return Resource.DataError(VPN_ERROR)
        }
        Log.e("TAG", "failure: e 44")
        return Resource.DataError(
            e?.message,
            ErrorResponseModel(
                e?.message,
                "",
                e?.message,
                0,
            )
        )
    }
}