package br.ifsp.moviedb.mvvm.interactor.details

import br.ifsp.moviedb.model.common.DataResult
import br.ifsp.moviedb.model.data.HomeDataModel
import br.ifsp.moviedb.mvvm.interactor.base.MVVMInteractor
import kotlinx.coroutines.flow.Flow

interface DetailsInteractor : MVVMInteractor {

    suspend fun onRetrieveFlowDetails(homeDataModel: HomeDataModel): Flow<DataResult<HomeDataModel>>
    suspend fun onRetrieveDetails(homeDataModel: HomeDataModel): DataResult<HomeDataModel>
    suspend fun updateFavourite(homeDataModel: HomeDataModel?): DataResult<HomeDataModel>
}
