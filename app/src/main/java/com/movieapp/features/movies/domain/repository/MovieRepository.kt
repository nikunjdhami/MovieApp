package com.movieapp.features.movies.domain.repository

import com.movieapp.common.ApiState
import com.movieapp.data.models.Movies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies() : Flow<ApiState<Movies>>
}