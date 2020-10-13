package com.android.testedbfilme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.clickbus.moviesdbtest.R
import com.clickbus.moviesdbtest.movies.callbacks.CreditsListener
import com.clickbus.moviesdbtest.movies.models.Crew
import com.clickbus.moviesdbtest.movies.models.Movie
import com.clickbus.moviesdbtest.movies.network.ApiCommunicationSingleton
import com.clickbus.moviesdbtest.view.MainActivity
import com.clickbus.moviesdbtest.view.fragments.HomeFragment

class DescriptionActivity : AppCompatActivity(),CreditsListener {
    private lateinit var crewsList: List<Crew>
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

        imgViewBackToHome.setOnClickListener{
            onBackPressed()
        }

        val extras = getIntent().getExtras()
        if (null != extras) {
            val id = extras.getInt("id") //usar o id para pesquisar no webservice os credits do filme
            val title = extras.getString("title")
            val img = extras.getString("img")
            val release_information = extras.getString("release_information")
//            val director = extras.getString("director")
//            val actor = extras.getString("actor")
//            val time = extras.getString("time")
//            val budget = extras.getString("budget")
//            val ticket = extras.getString("ticket")

            ApiCommunicationSingleton.loadCredits(this,id)

            movieId = id

            txtTitle.text = title
            txtReleaseDate.text = release_information

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/original${img}")
                .transform(CenterCrop())
                .into(imgView)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSuccess(listcrews: List<Crew>) {
        crewsList = listcrews

        val m = crewsList.get(4)
        txtDirector.text = m.name

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original${m.profile_path}")
            .transform(CenterCrop())
            .into(imgViewDirector)
    }

    override fun onFailure(message: String?) {
        Toast.makeText(applicationContext, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}