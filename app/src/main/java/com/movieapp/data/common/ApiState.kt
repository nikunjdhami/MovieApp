package com.movieapp.data.common

sealed class ApiState<out T>{
    object Loading : ApiState<Nothing>()
}