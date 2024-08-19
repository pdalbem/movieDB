package br.ifsp.moviedb.network.client

import br.ifsp.moviedb.common.Definitions
import br.ifsp.moviedb.model.parsers.movie.MovieResponse
import br.ifsp.moviedb.model.parsers.search.SearchResponse
import br.ifsp.moviedb.model.parsers.theatre.TheatreResponse
import br.ifsp.moviedb.model.parsers.tv.TvShowResponse
import br.ifsp.moviedb.network.api.MovieApi
import javax.inject.Inject

class MovieClient @Inject constructor(private var movieApi: MovieApi) {

    suspend fun getSearchAsync(queryText: String, page: Int): SearchResponse {
        return movieApi.getSearchAsync(Definitions.API_KEY, queryText, page)
    }

    suspend fun getMovieDetailsAsync(movieId: Int): MovieResponse {
        return movieApi.getMovieDetailsAsync(movieId, Definitions.API_KEY, Definitions.VIDEOS)
    }

    suspend fun getTvShowDetailsAsync(tvId: Int): TvShowResponse {
        return movieApi.getTvShowDetailsAsync(tvId, Definitions.API_KEY, Definitions.VIDEOS)
    }

    suspend fun getMovieTheatre(startDate: String, endDate: String): TheatreResponse {
        return movieApi.getMovieTheatreAsync(startDate, endDate, Definitions.API_KEY)
    }
}