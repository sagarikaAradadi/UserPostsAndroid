package com.conduent.nationalhighways.data.error.errorUsecase

import com.samplerecyclerview.ui.utils.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
