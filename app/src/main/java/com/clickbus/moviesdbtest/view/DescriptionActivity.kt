@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "SENSELESS_COMPARISON")

package com.android.testedbfilme

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.clickbus.moviesdbtest.R
import com.clickbus.moviesdbtest.controller.CastAdapter
import com.clickbus.moviesdbtest.controller.MoviesAdapter
import com.clickbus.moviesdbtest.movies.callbacks.CastListener
import com.clickbus.moviesdbtest.movies.callbacks.CrewListener
import com.clickbus.moviesdbtest.movies.callbacks.DetailListener
import com.clickbus.moviesdbtest.movies.models.Cast
import com.clickbus.moviesdbtest.movies.models.Crew
import com.clickbus.moviesdbtest.movies.models.MovieDetail
import com.clickbus.moviesdbtest.movies.network.ApiCommunicationSingleton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DescriptionActivity : AppCompatActivity(),CrewListener,DetailListener,CastListener {
    private lateinit var recyclerviewMovie: RecyclerView
    private lateinit var castAdapter: CastAdapter
    private lateinit var crewsList: List<Crew>
    private lateinit var detailMovie: MovieDetail
    private lateinit var txtTitle: TextView
    private lateinit var txtDescription: TextView
    private lateinit var txtDuration: TextView
    private lateinit var txtDirector: TextView
    private lateinit var txtReleaseDate: TextView
    private lateinit var txtOrder: TextView
    private lateinit var txtTicket: TextView
    private lateinit var imgView: ImageView
    private lateinit var imgViewBackToHome: ImageView
    private lateinit var imgViewDirector: ImageView
    private lateinit var ratingMovie: RatingBar
    private lateinit var mediaVotesMovie: TextView
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtTitle = findViewById(R.id.textView_title_desciption_value)
        txtDescription = findViewById(R.id.textView_description_text)
        txtDuration = findViewById(R.id.textView_time_duration_value)
        txtDirector = findViewById(R.id.textView_director_name_value)
        txtReleaseDate = findViewById(R.id.textView_date_release_value)
        txtOrder = findViewById(R.id.textView_order_value)
        txtTicket = findViewById(R.id.textView_ticket_value)
        imgView = findViewById(R.id.imageView_img_desciption_value)
        imgViewBackToHome = findViewById(R.id.imageButton_back_to_home)
        imgViewDirector = findViewById(R.id.imageView_director_image)
        ratingMovie = findViewById(R.id.ratingBar_popularity_description)
        mediaVotesMovie = findViewById(R.id.textView_media_votes_value)

        imgViewBackToHome.setOnClickListener{
            onBackPressed()
        }

        recyclerviewMovie = findViewById(R.id.recyclerview_actors)
        recyclerviewMovie.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        castAdapter = CastAdapter(listOf())
        recyclerviewMovie.adapter = castAdapter

        val extras = getIntent().getExtras()
        if (null != extras) {
            val id = extras.getInt("id") //usar o id para pesquisar no webservice os credits do filme
            val title = extras.getString("title")
            val img = extras.getString("img")
            val release_information = extras.getString("release_information")
            val rating = extras.getDouble("rating")

            ApiCommunicationSingleton.loadCrew(this, id)
            ApiCommunicationSingleton.loadDetails(this, id)
            ApiCommunicationSingleton.loadCast(this,id)

            movieId = id

            ratingMovie.rating = rating.toFloat()/2
            mediaVotesMovie.text = rating.toString()

            txtTitle.text = title
            txtReleaseDate.text = release_information



        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSuccess(listcrews: List<Crew>) {
        crewsList = listcrews

        val m = crewsList.get(0)
        txtDirector.text = m.name

        if (m.profile_path != null){

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/original${m.profile_path}")
                .transform(CenterCrop())
                .into(imgViewDirector)

        }else{

            Glide.with(this)
                .load(R.drawable.ic_no_perfil)
                .transform(CenterCrop())
                .into(imgViewDirector)
        }

    }

    @SuppressLint("SimpleDateFormat")
    override fun onSuccessDetail(movieDetail: MovieDetail) {
        detailMovie = movieDetail

        var sdf = SimpleDateFormat("mm")

        try {
            val dt: Date = sdf.parse(detailMovie.runtime.toString())
            sdf = SimpleDateFormat("HH:mm")

            txtDuration.text = sdf.format(dt)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        txtDescription.text = detailMovie.overview
        txtOrder.text = movieDetail.budget.toString()
        txtTicket.text = detailMovie.revenue.toString()

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/original${detailMovie.posterPath}")
                .transform(CenterCrop())
                .into(imgView)


    }


    override fun onFailureDetail(message: String?) {
        Toast.makeText(
            applicationContext,
            getString(R.string.error_fetch_movies),
            Toast.LENGTH_SHORT
        ).show()
    }


    override fun onFailure(message: String?) {
        Toast.makeText(
            applicationContext,
            getString(R.string.error_fetch_movies),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onSucessCast(listcast: List<Cast>) {
        castAdapter.updateMovies(listcast)
    }

    override fun onFailureCast(message: String?) {
        TODO("Not yet implemented")
    }


}