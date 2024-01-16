package com.damras.arabe

import androidx.room.Room
import com.damras.arabe.app.addword.AddWord
import com.damras.arabe.app.addword.AddWordViewModel
import com.damras.arabe.app.main.MainViewModel
import com.damras.arabe.data.database.AppDatabase
import com.damras.arabe.data.WordRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Database
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DB_NAME).allowMainThreadQueries().build()
    } bind AppDatabase::class

    // Repositories
    single { WordRepository(get()) }

    // ViewModels
    viewModel { MainViewModel(get()) }
    viewModel { AddWordViewModel(get()) }
}