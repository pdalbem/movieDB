package br.ifsp.moviedb.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import br.ifsp.moviedb.common.SingleLiveEvent
import br.ifsp.moviedb.model.data.HomeDataModel
import br.ifsp.moviedb.model.data.MovieDataModel
import br.ifsp.moviedb.mvvm.interactor.home.HomeInteractorImpl
import br.ifsp.moviedb.mvvm.viewModel.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractorImpl
) : BaseViewModel() {

    private var theatreMutableLiveData: SingleLiveEvent<MutableList<MovieDataModel>> =
        SingleLiveEvent()

    private var queryText = ""

    fun getMovieInTheatre(): SingleLiveEvent<MutableList<MovieDataModel>> {
        return theatreMutableLiveData
    }

    private val currentQuery = MutableLiveData("")

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState


    fun searchMovies() {
        currentQuery.value = queryText
    }

    fun setQueryText(queryText: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        val resul = homeInteractor.flowPaging(queryText).cachedIn(viewModelScope)
        delay(200)

        _uiState.update { it.copy(isLoading = false, data = resul) }
    }


    fun fetchMovieInTheatre() {
        loadingLiveData.value = true
        uiScope.launch {
            val response = homeInteractor.getMoviesInTheatres()
            loadingLiveData.value = false
            theatreMutableLiveData.value = response.data?.toMutableList()
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: Flow<PagingData<HomeDataModel>>? = null
)