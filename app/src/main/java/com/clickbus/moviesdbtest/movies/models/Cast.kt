package com.clickbus.moviesdbtest.movies.models

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("caracter") val caracter: String,
    @SerializedName("credit_id") val credit_id: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int,
    @SerializedName("profile_path") val profile_path: String
)