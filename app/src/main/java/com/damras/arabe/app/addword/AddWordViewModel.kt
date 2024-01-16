package com.damras.arabe.app.addword

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.damras.arabe.app.navigation.Screen
import com.damras.arabe.data.WordRepository
import com.damras.arabe.model.Dialect
import com.damras.arabe.model.Word

class AddWordViewModel(
    private val wordRepository: WordRepository
): ViewModel() {


    fun addWord(french: String, arabic: String, transcription: String, dialect: Dialect) {
        wordRepository.addWord(Word(0, french, arabic, dialect, transcription))
    }

    fun navigateBack(navController: NavController) {
        navController.popBackStack()
    }
}