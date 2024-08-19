package br.ifsp.moviedb.mvvm.interactor.home

import androidx.paging.PagingData
import br.ifsp.moviedb.model.common.DataResult
import br.ifsp.moviedb.model.data.HomeDataModel
import br.ifsp.moviedb.model.data.MovieDataModel
import br.ifsp.moviedb.mvvm.interactor.base.MVVMInteractor
import kotlinx.coroutines.flow.Flow

interface HomeInteractor : MVVMInteractor {

    suspend fun onRetrieveSearchResult(queryText: String, page: Int): Flow<DataResult<List<HomeDataModel>>>
    suspend fun getMoviesInTheatres():DataResult<List<MovieDataModel>>

   suspend fun flowPaging(queryText: String): Flow<PagingData<HomeDataModel>>
}
