package com.clickbus.moviesdbtest.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.clickbus.moviesdbtest.R
import com.clickbus.moviesdbtest.movies.callbacks.GenreListListener
import com.clickbus.moviesdbtest.movies.models.Genre
import com.clickbus.moviesdbtest.movies.network.ApiCommunicationSingleton

class FilterSearchActivity : AppCompatActivity(), GenreListListener {
    private lateinit var toolbar:Toolbar
    private lateinit var listGenres: List<Genre>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_search)

        toolbar = findViewById(R.id.toolbar_filter)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        ApiCommunicationSingleton.loadGenresWithLanguage(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSuccess(list: List<Genre>) {
        listGenres = list
        val a = listGenres
    }

    override fun onFailure(message: String?) {

    }
}