package com.movieapp.data.repository

import com.movieapp.common.ApiState
import com.movieapp.common.base.BaseRepository
import com.movieapp.features.movies.domain.repository.MovieRepository
import com.movieapp.data.models.Movies
import com.movieapp.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor (private val apiService: ApiService)
    : MovieRepository, BaseRepository() {
    override suspend fun getMovies(): Flow<ApiState<Movies>> = safeApiCall{
        apiService.getMovies()
    }
}