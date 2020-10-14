package com.clickbus.moviesdbtest.movies.callbacks
import com.clickbus.moviesdbtest.movies.models.MovieDetail

interface DetailListener {
    fun onSuccessDetail(movieDetail: MovieDetail)
    fun onFailureDetail(message: String? = null)
}