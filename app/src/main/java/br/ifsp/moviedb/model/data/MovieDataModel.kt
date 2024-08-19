package br.ifsp.moviedb.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDataModel(val id: Int? = null,
                          val title: String? = "-",
                          val summary: String? = "-",
                          var thumbnail: String? = "",
                          val releaseDate: String? = "-") : Parcelable