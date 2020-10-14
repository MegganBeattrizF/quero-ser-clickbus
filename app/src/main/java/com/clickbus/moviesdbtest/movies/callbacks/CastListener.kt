package com.clickbus.moviesdbtest.movies.callbacks

import com.clickbus.moviesdbtest.movies.models.Cast


interface CastListener {
    fun onSucessCast(listcast: List<Cast>)
    fun onFailureCast(message: String? = null)
}