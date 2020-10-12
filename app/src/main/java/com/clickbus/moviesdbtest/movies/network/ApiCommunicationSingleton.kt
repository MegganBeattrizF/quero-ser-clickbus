package com.clickbus.moviesdbtest.movies.network

import android.util.Log
import com.clickbus.moviesdbtest.BuildConfig
import com.clickbus.moviesdbtest.movies.callbacks.GenreListListener
import com.clickbus.moviesdbtest.movies.callbacks.MovieListListener
import com.clickbus.moviesdbtest.movies.models.GenreListContainer
import com.clickbus.moviesdbtest.movies.models.MovieListPageResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCommunicationSingleton {

        private val api: TmdbService

        init {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            api = retrofit.create(TmdbService::class.java)
        }

    fun getPopularMovies(page: Int = 1) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<MovieListPageResult> {
                override fun onResponse(
                    call: Call<MovieListPageResult>,
                    response: Response<MovieListPageResult>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Movies: ${responseBody.movieList}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<MovieListPageResult>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }


//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//    private val tmdbService by lazy { retrofit.create(TmdbService::class.java) }
//
//    fun loadGenres(genreListListener: GenreListListener) {
//        tmdbService.genres(BuildConfig.API_KEY).enqueue(object : Callback<GenreListContainer> {
//            override fun onResponse(
//                call: Call<GenreListContainer>,
//                response: Response<GenreListContainer>
//            ) {
//                response.body()?.genres?.let {
//                    genreListListener.onSuccess(it)
//                    return
//                }
//                genreListListener.onFailure()
//            }
//
//            override fun onFailure(call: Call<GenreListContainer>, t: Throwable) {
//                genreListListener.onFailure(t.message)
//            }
//        })
//    }
//
//    fun loadMostPopularMoviesToday(movieListListener: MovieListListener) {
//        tmdbService.trendingMovies(BuildConfig.API_KEY).enqueue(
//            object : Callback<MovieListPageResult> {
//                override fun onResponse(
//                    call: Call<MovieListPageResult>,
//                    response: Response<MovieListPageResult>
//                ) {
//                    response.body()?.movieList?.let {
//                        movieListListener.onSuccess(it)
//                        return
//                    }
//                    movieListListener.onFailure()
//                }
//
//                override fun onFailure(call: Call<MovieListPageResult>, t: Throwable) {
//                    movieListListener.onFailure(t.message)
//                }
//            })
//    }
}