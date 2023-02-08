package com.movieapp.features.movies.domain.usecases

import com.movieapp.common.ApiState
import com.movieapp.common.map
import com.movieapp.features.movies.domain.mapper.MovieMapper
import com.movieapp.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val mapper: MovieMapper
)  {

    suspend fun getMovies() : Flow<ApiState<List<com.movieapp.data.models.Result?>>>{
        return repository.getMovies().map {
            it.map {
                mapper.fromMap(it)
            }
        }
    }
}