package com.damras.arabe.data

import com.damras.arabe.data.database.AppDatabase
import com.damras.arabe.model.Dialect
import com.damras.arabe.model.Word
import com.damras.arabe.model.toModel
import com.damras.arabe.data.database.entity.Word as WordEntity

class WordRepository(
    private val database: AppDatabase
) {
    fun getAllWords() : List<Word> {
        return database.wordDao().getAll().map { it.toModel() }
    }

    fun addWord(word: Word) {
        database.wordDao().insertOne(word.toEntity())
    }

    fun removeWord(word: Word) {
        database.wordDao().delete(word.toEntity())
    }
}