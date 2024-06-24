package com.samplerecyclerview.ui.utils

import com.conduent.nationalhighways.data.error.errorUsecase.ErrorUseCase
import com.conduent.nationalhighways.data.error.mapper.ErrorMapper
import com.samplerecyclerview.ui.utils.error.Error


class ErrorManager(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return com.samplerecyclerview.ui.utils.error.Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}