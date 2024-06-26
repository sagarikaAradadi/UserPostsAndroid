package com.samplerecyclerview.ui.utils.error

class Error(val code: Int, val description: String) {

    constructor(exception: Exception) : this(
        code = DEFAULT_ERROR, description = exception.message
            ?: ""
    )

}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3
const val PASS_WORD_ERROR = -101
const val USER_NAME_ERROR = -102
const val CHECK_YOUR_FIELDS = 401
const val UNKNOWN_ERROR = -104
const val SERVER_ERROR = 500
const val TOO_MANY_LOGIN_ATTEMPT = 5260
const val CREATE_ACCOUNT_FAILED = 5260