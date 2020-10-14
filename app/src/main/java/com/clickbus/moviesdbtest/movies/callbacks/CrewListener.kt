package com.clickbus.moviesdbtest.movies.callbacks

import com.clickbus.moviesdbtest.movies.models.Crew
import com.clickbus.moviesdbtest.movies.models.Genre
import com.clickbus.moviesdbtest.movies.models.Movie

interface CrewListener {
    fun onSuccess(listcrews: List<Crew>)
    fun onFailure(message: String? = null)

}