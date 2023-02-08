package com.movieapp.features.movies.domain.mapper

import com.movieapp.common.base.Mapper
import com.movieapp.data.models.Movies
import com.movieapp.data.models.Result
import javax.inject.Inject

class MovieMapper @Inject constructor() :Mapper<Movies?, List<com.movieapp.data.models.Result?>> {
    override fun fromMap(from: Movies?): List<Result?> {
        return from?.results?.map {
            com.movieapp.data.models.Result(
                id = it.id,
                original_title = it.original_title,
                overview = it.overview,
                poster_path = it.poster_path,
                vote_average = it.vote_average

            )
        }!!
    }
}