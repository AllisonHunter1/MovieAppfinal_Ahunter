package com.example.movieapp.utils_view_state

//import com.example.movieapp.data.MoviesDataSource
import com.example.movieapp.network.MoviePhoto

data class MoviesUiState(
    val moviesList: List<MoviePhoto> = emptyList(),
    val currentMovie: MoviePhoto? = null,
    val isShowingListPage: Boolean = true
)