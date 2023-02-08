package com.movieapp.common.base

interface Mapper<F, T> {

    fun fromMap(from : F ) : T
}