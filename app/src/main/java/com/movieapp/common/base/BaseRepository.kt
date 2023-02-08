package com.movieapp.common.base

import com.movieapp.common.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

// will be used in all repository in project
abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apicall: suspend () -> Response<T>
    )
            : Flow<ApiState<T>> = flow {
        emit(ApiState.Loading)

        val response = apicall();

        if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                emit(ApiState.Success(data))
            } else {
                val error = response.errorBody()
                if (error != null) {
                    emit(ApiState.Failure(IOException(error.toString())))
                } else {
                    emit(ApiState.Failure(IOException("Something Went Wrong!!")))
                }
            }
        } else {
            emit(ApiState.Failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(ApiState.Failure(java.lang.Exception(it)))
    }.flowOn(Dispatchers.IO)

}
