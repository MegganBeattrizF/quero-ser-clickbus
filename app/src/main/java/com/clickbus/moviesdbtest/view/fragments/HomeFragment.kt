package com.clickbus.moviesdbtest.view.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.testedbfilme.DescriptionActivity
import com.clickbus.moviesdbtest.R
import com.clickbus.moviesdbtest.controller.MoviesAdapter
import com.clickbus.moviesdbtest.helper.RecyclerItemClickListener
import com.clickbus.moviesdbtest.movies.callbacks.MovieListListener
import com.clickbus.moviesdbtest.movies.models.Movie
import com.clickbus.moviesdbtest.movies.network.ApiCommunicationSingleton
import com.clickbus.moviesdbtest.view.FilterSearchActivity

@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment(),MovieListListener {

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    private lateinit var recyclerviewMovie: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var movieList: List<Movie>
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_home, container,
            false
        )

        toolbarConfig(view)
        recyclerConfig(view)

        ApiCommunicationSingleton.loadMostPopularMoviesToday(this)

        return view
    }

    private fun recyclerConfig(view:View) {
        recyclerviewMovie = view.findViewById(R.id.RecyclerView_movies)
        recyclerviewMovie.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        popularMoviesAdapter = MoviesAdapter(listOf())
        recyclerviewMovie.adapter = popularMoviesAdapter


        recyclerviewMovie.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                recyclerviewMovie, object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View?, position: Int) {
                        val m = movieList.get(position)
                        val intent = Intent(context, DescriptionActivity::class.java)
                        intent.putExtra("id", m.id)
                        intent.putExtra("title", m.title)
                        intent.putExtra("img", m.posterPath)
                        intent.putExtra("release_information", m.releaseDate)
                        intent.putExtra("rating", m.voteAverage)
                        startActivity(intent)

                    }

                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        TODO("Not yet implemented")
                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                })
        )
    }

    private fun toolbarConfig(view: View) {
        toolbar = view.findViewById(R.id.toolbar_home)
        toolbar.title = "Populares hoje"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.inflateMenu(R.menu.toolbar_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_filter ->{

                    val intent = Intent(context, FilterSearchActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }
    }

    override fun onSuccess(list: List<Movie>) {
        popularMoviesAdapter.updateMovies(list)
        movieList = list
    }


    override fun onFailure(message: String?) {
        Toast.makeText(context, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}