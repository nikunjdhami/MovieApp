package com.movieapp.features.movies.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.common.doOnFailure
import com.movieapp.common.doOnLoading
import com.movieapp.common.doOnSuccess
import com.movieapp.data.models.Result
import com.movieapp.features.movies.domain.usecases.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCase: MovieUseCase
) : ViewModel() {
    private val response: MutableState<MovieState> = mutableStateOf(MovieState())
    val _res: State<MovieState> = response

    init {
        viewModelScope.launch {
            useCase.getMovies()
                .doOnSuccess {
                    response.value = MovieState(
                        it
                    )
            }.doOnFailure {
                response.value = MovieState(
                    error = it?.message!!
                )
            }.doOnLoading {
                response.value = MovieState(
                    isLoading = true
                )
            }.collect()

        }
    }
}

data class MovieState(
    val data: List<Result?> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = true
)