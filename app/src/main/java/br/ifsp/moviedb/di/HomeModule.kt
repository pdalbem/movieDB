package br.ifsp.moviedb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import br.ifsp.moviedb.database.MovieDbDatabase
import br.ifsp.moviedb.model.mappers.HomeDataModelMapperImpl
import br.ifsp.moviedb.mvvm.interactor.home.HomeInteractorImpl
import br.ifsp.moviedb.network.client.MovieClient

@InstallIn(SingletonComponent::class)
@Module
object HomeModule {

    @Provides
    fun provideHomeInteractor(
        movieClient: MovieClient,
        movieDbDatabase: MovieDbDatabase,
        mapperImpl: HomeDataModelMapperImpl
    ): HomeInteractorImpl {
        return HomeInteractorImpl(movieClient, movieDbDatabase, mapperImpl)
    }
}
