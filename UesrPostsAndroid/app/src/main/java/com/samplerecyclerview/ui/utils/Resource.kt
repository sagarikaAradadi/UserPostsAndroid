package com.samplerecyclerview.ui.utils

import com.conduent.nationalhighways.data.model.ErrorResponseModel

sealed class Resource<T>(
    val data: T? = null,
    val errorMsg: String = "",
    val errorModel: ErrorResponseModel? = null,
    val errorCode: Int = 0,
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)

    //class DataError<T>(errorCode: Int) : Resource<T>(null, errorCode)
    class DataError<T>(msg: String?, errorData: ErrorResponseModel? = null) : Resource<T>(
        null, msg ?: "",
        errorData
    )

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorMsg]"
            is Loading<T> -> "Loading"
        }
    }
}
