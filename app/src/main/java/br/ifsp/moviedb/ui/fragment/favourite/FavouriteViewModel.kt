package br.ifsp.moviedb.ui.fragment.favourite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import br.ifsp.moviedb.model.data.HomeDataModel
import br.ifsp.moviedb.mvvm.interactor.favourite.FavouriteInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteInteractorImpl: FavouriteInteractorImpl) : ViewModel() {

    var state by mutableStateOf(FavouriteUiState())

    init {
        readWatchListFromDatabase()
    }

    fun readWatchListFromDatabase() {
        state.loading = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) { favouriteInteractorImpl.fetchWatchListFromDatabase() }.collect { response ->
                response.data?.let {
                    state = state.copy(initialFavourite = it)
                } ?: response.throwable?.let {
                    Timber.e(it.toString())
                }
                state = state.copy(loading = false)
            }
        }
    }
}


data class FavouriteUiState(
        var initialFavourite: List<HomeDataModel> = emptyList(),
        var loading: Boolean = false
){

    var favouriteList by mutableStateOf(initialFavourite)
}
