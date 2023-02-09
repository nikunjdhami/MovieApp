package com.movieapp.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform


sealed class ApiState<out T> {

    // out key word in generics means it can also accepts subclassed of type T
    data class Success<out R>(val data : R) : ApiState<R>()
    data class Failure(val msg : Throwable) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success -> "$data"
            is Failure -> "${msg.message}"
            is Loading -> "Loading"

        }
    }


}

fun <T,R> ApiState<T>.map(transform : (T) -> R) : ApiState<R>{
    return when(this){
        is ApiState.Success -> ApiState.Success(transform(data))
        is ApiState.Failure -> ApiState.Failure(msg)
        ApiState.Loading -> ApiState.Loading
    }
}

fun <T> Flow<ApiState<T>>.doOnSuccess( action : suspend (T) -> Unit) : Flow<ApiState<T>> = transform {
    if (it is ApiState.Success){
        action(it.data)
    }
    return@transform emit(it)
}

fun <T> Flow<ApiState<T>>.doOnFailure( action : suspend (Throwable?) -> Unit) : Flow<ApiState<T>> = transform {
    if (it is ApiState.Failure){
        action(it.msg)
    }
    return@transform emit(it)
}

fun <T> Flow<ApiState<T>>.doOnLoading ( action: suspend () -> Unit) : Flow<ApiState<T>> = transform {
    if (it is ApiState.Loading){
        action()
    }
    return@transform emit(it)
}

