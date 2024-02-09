package com.osamaaftab.bankmisr.data.repository.base

import com.osamaaftab.bankmisr.data.ApiErrorHandle
import com.osamaaftab.bankmisr.domain.model.ErrorModel
import com.osamaaftab.bankmisr.domain.model.Resource
import com.osamaaftab.bankmisr.domain.model.Response
import kotlinx.coroutines.Deferred

open class BaseRepositoryImp(val errorHandle: ApiErrorHandle) {

    suspend inline fun <reified T : Response> Deferred<T>.awaitAndCatch(): Resource<T> {
        return try {
            val result = this.await()
            if (result.success) {
                Resource.Success(result)
            } else {
                Resource.Error(
                    errorModel = ErrorModel(
                        result.error?.info,
                        result.error?.code,
                        ErrorModel.ErrorStatus.NOT_DEFINED
                    )
                )
            }
        } catch (e: Exception) {
            Resource.Error(errorModel = errorHandle.traceErrorException(e))
        }
    }
}