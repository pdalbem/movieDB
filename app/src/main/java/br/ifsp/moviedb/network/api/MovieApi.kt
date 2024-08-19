package br.ifsp.moviedb.network.api

import br.ifsp.moviedb.model.parsers.movie.MovieResponse
import br.ifsp.moviedb.model.parsers.search.SearchResponse
import br.ifsp.moviedb.model.parsers.theatre.TheatreResponse
import br.ifsp.moviedb.model.parsers.tv.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("search/multi")
    suspend fun getSearchAsync(@Query("api_key") apiKey: String,
                               @Query("query") query: String,
                               @Query("page") page: Int): SearchResponse


    @GET("movie/{movieId}")
    suspend fun getMovieDetailsAsync(@Path("movieId") movieId: Int,
                                     @Query("api_key") apiKey: String,
                                     @Query("append_to_response") appendToResponse: String): MovieResponse


    @GET("tv/{tvId}")
    suspend fun getTvShowDetailsAsync(@Path("tvId") movieId: Int,
                                      @Query("api_key") apiKey: String,
                                      @Query("append_to_response") appendToResponse: String): TvShowResponse

    @GET("discover/movie")
    suspend fun getMovieTheatreAsync(@Query("primary_release_date.gte") startDate: String,
                                     @Query("primary_release_date.lte") endDate: String,
                                     @Query("api_key") apiKey: String): TheatreResponse
}