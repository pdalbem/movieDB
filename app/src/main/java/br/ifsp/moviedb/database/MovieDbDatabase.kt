package br.ifsp.moviedb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.ifsp.moviedb.database.dao.MovieDbTable
import br.ifsp.moviedb.database.dao.MovieDbTableDao

@Database(entities = [MovieDbTable::class], version = 1, exportSchema = false)
abstract class MovieDbDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "MovieDB"

        fun get(context: Context): MovieDbDatabase {
            return Room.databaseBuilder(context.applicationContext, MovieDbDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    abstract fun movieDbTableDao(): MovieDbTableDao
}