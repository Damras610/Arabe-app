package com.damras.arabe.app.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.damras.arabe.app.navigation.Screen
import com.damras.arabe.data.WordRepository
import com.damras.arabe.model.MultiDialectWord
import com.damras.arabe.model.Word
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val wordRepository: WordRepository
): ViewModel() {



    enum class DICTIONARY_MODE {
        FRENCH_TO_ARABIC,
        ARABIC_TO_FRENCH
    }

    val dictionaryMode = mutableStateOf(DICTIONARY_MODE.FRENCH_TO_ARABIC)
    val frenchToArabic = MutableStateFlow(getAllWordsFrenchToArabic())
    val arabicToFrench = MutableStateFlow(getAllWordsArabicToFrench())


    fun getAllWordsFrenchToArabic(): Map<Char, List<MultiDialectWord>> {
        val words = wordRepository.getAllWords()
        return words.groupBy {
            it.french
        }.map {
            MultiDialectWord(it.key, it.value)
        }.sortedBy {
            it.french
        }.groupBy {
            it.french.first()
        }
    }

    fun getAllWordsArabicToFrench(): Map<Char, List<Word>> {
        val words = wordRepository.getAllWords()
        return words.sortedBy {
            it.arabic
        }.groupBy {
            it.arabic.first()
        }
    }

    fun switchDictionaryMode() {
        dictionaryMode.value = when (dictionaryMode.value) {
            DICTIONARY_MODE.FRENCH_TO_ARABIC -> DICTIONARY_MODE.ARABIC_TO_FRENCH
            DICTIONARY_MODE.ARABIC_TO_FRENCH -> DICTIONARY_MODE.FRENCH_TO_ARABIC
        }
    }

    fun navigateToAddWord(navController: NavController) {
        navController.navigate(Screen.AddWord.route)
    }

}