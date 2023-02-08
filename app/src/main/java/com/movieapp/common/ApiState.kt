package com.movieapp.common




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

