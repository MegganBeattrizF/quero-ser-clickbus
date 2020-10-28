package com.clickbus.moviesdbtest.movies.network

import com.clickbus.moviesdbtest.BuildConfig
import com.clickbus.moviesdbtest.movies.callbacks.*
import com.clickbus.moviesdbtest.movies.models.Credits
import com.clickbus.moviesdbtest.movies.models.GenreListContainer
import com.clickbus.moviesdbtest.movies.models.MovieDetail
import com.clickbus.moviesdbtest.movies.models.MovieListPageResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCommunicationSingleton {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val tmdbService by lazy { retrofit.create(TmdbService::class.java) }

    fun loadGenresWithLanguage(genreListListener: GenreListListener) {
        tmdbService.genresWithLanguage(BuildConfig.API_KEY,"pt-BR").enqueue(
            object : Callback<GenreListContainer> {
            override fun onResponse(
                call: Call<GenreListContainer>,
                response: Response<GenreListContainer>
            ) {
                response.body()?.genres?.let {
                    genreListListener.onSuccess(it)
                    return
                }
                genreListListener.onFailure()
            }

            override fun onFailure(call: Call<GenreListContainer>, t: Throwable) {
                genreListListener.onFailure(t.message)
            }
        })
    }

    fun loadMostPopularMoviesToday(movieListListener: MovieListListener) {
        tmdbService.trendingMovies(BuildConfig.API_KEY,).enqueue(
            object : Callback<MovieListPageResult> {
                override fun onResponse(
                    call: Call<MovieListPageResult>,
                    response: Response<MovieListPageResult>
                ) {
                    response.body()?.movieList?.let {
                        movieListListener.onSuccess(it)
                        return
                    }
                    movieListListener.onFailure()
                }

                override fun onFailure(call: Call<MovieListPageResult>, t: Throwable) {
                    movieListListener.onFailure(t.message)
                }
            })
    }

    fun loadCrew(movieCrewListener: CrewListener, movieId: Int ) {

        tmdbService.getCredits(movieId,BuildConfig.API_KEY).enqueue(
            object : Callback<Credits>{
                override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                    response.body()?.crewList?.let {
                        movieCrewListener.onSuccess(it)
                        return
                    }
                    movieCrewListener.onFailure()
                }

                override fun onFailure(call: Call<Credits>, t: Throwable) {

                }

            }
        )

    }
    fun loadCast(movieCastListener: CastListener, movieId: Int){
        tmdbService.getCredits(movieId,BuildConfig.API_KEY).enqueue(
            object : Callback<Credits>{
                override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                    response.body()?.castList?.let {
                        movieCastListener.onSucessCast(it)
                        return
                    }
                }

                override fun onFailure(call: Call<Credits>, t: Throwable) {
                    movieCastListener.onFailureCast()
                }

            }
        )
    }

    fun loadDetails(movieDetailListener: DetailListener, movieId: Int){
        tmdbService.movieDetail(movieId,BuildConfig.API_KEY,"pt-BR").enqueue(
            object : Callback<MovieDetail>{
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    response.body()?.let {
                        movieDetailListener.onSuccessDetail(it)
                        return
                    }
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    movieDetailListener.onFailureDetail()
                }

            }
        )

    }
}