package com.clickbus.moviesdbtest.movies.models

import com.google.gson.annotations.SerializedName

data class Credits(

    @SerializedName("id") val id: Int,
    @SerializedName("cast") val castList: List<Cast>,
    @SerializedName("crew") val crewList: List<Crew>,

)


