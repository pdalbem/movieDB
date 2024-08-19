package br.ifsp.moviedb.mvvm.interactor.details

import br.ifsp.moviedb.common.Definitions
import br.ifsp.moviedb.database.MovieDbDatabase
import br.ifsp.moviedb.database.dao.MovieDbTable
import br.ifsp.moviedb.model.common.DataResult
import br.ifsp.moviedb.model.data.HomeDataModel
import br.ifsp.moviedb.model.parsers.movie.MovieResponse
import br.ifsp.moviedb.model.parsers.tv.TvShowResponse
import br.ifsp.moviedb.mvvm.interactor.base.BaseInteractor
import br.ifsp.moviedb.network.client.MovieClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class DetailsInteractorImpl @Inject constructor(private var movieClient: MovieClient, private val movieDbDatabase: MovieDbDatabase) : BaseInteractor(), DetailsInteractor {

    override suspend fun onRetrieveFlowDetails(homeDataModel: HomeDataModel): Flow<DataResult<HomeDataModel>> {
        return flow {
            emit(onRetrieveDetails(homeDataModel))
        }
    }

    override suspend fun onRetrieveDetails(homeDataModel: HomeDataModel): DataResult<HomeDataModel> {
        return try {
            val response = when (homeDataModel.mediaType) {
                Definitions.IS_MOVIE -> movieToHomeDataModel(homeDataModel, movieClient.getMovieDetailsAsync(homeDataModel.id
                        ?: 0))
                else -> tvShowToHomeDataModel(homeDataModel, movieClient.getTvShowDetailsAsync(homeDataModel.id
                        ?: 0))
            }
            DataResult(response)
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(throwable = t)
        }
    }

    override suspend fun updateFavourite(homeDataModel: HomeDataModel?): DataResult<HomeDataModel> {
        return try {
            homeDataModel?.let {
                if (homeDataModel.isFavorite) {
                    movieDbDatabase.movieDbTableDao().insertModel(toDatabaseModel(homeDataModel))
                } else {
                    it.id?.let { id -> movieDbDatabase.movieDbTableDao().deleteModel(id) }
                }
            }
            DataResult(homeDataModel)
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(throwable = t)
        }
    }

    private fun toDatabaseModel(homeDataModel: HomeDataModel): MovieDbTable {
        return MovieDbTable(
                id = homeDataModel.id ?: 0,
                title = homeDataModel.title ?: "-",
                mediaType = homeDataModel.mediaType ?: "-",
                summary = homeDataModel.summary ?: "-",
                thumbnail = homeDataModel.thumbnail ?: "",
                releaseDate = homeDataModel.releaseDate ?: "-",
                ratings = homeDataModel.ratings ?: "-",
                isFavourite = homeDataModel.isFavorite,
                genresName = homeDataModel.genresName ?: "-",
                videoKey = homeDataModel.videoKey ?: "",
                videoUrl = homeDataModel.videoUrl ?: "",
                dateAdded = System.currentTimeMillis())
    }

    private fun movieToHomeDataModel(homeDataModel: HomeDataModel, movieResponse: MovieResponse): HomeDataModel {
        homeDataModel.thumbnail = "${Definitions.IMAGE_URL_W500}${movieResponse.posterPath}"
        homeDataModel.genresName = movieResponse.genres?.firstOrNull()?.name ?: "-"
        homeDataModel.videoKey = movieResponse.videos?.videoResultList?.firstOrNull()?.key ?: ""
        homeDataModel.videoUrl = "<html><body><br><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${homeDataModel.videoKey}\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
        return homeDataModel
    }

    private fun tvShowToHomeDataModel(homeDataModel: HomeDataModel, tvShowResponse: TvShowResponse): HomeDataModel {
        homeDataModel.thumbnail = "${Definitions.IMAGE_URL_W500}${tvShowResponse.posterPath}"
        homeDataModel.genresName = tvShowResponse.genres?.firstOrNull()?.name ?: "-"
        homeDataModel.videoKey = tvShowResponse.videos?.videoResultList?.firstOrNull()?.key ?: ""
        homeDataModel.videoUrl = "<html><body><br><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${homeDataModel.videoKey}\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
        return homeDataModel
    }

}
