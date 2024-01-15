package com.damras.arabe.data

import com.damras.arabe.data.database.AppDatabase
import com.damras.arabe.model.Dialect
import com.damras.arabe.model.Word
import com.damras.arabe.data.database.entity.Word as WordEntity

class WordRepository(
    private val database: AppDatabase
) {
    fun getAllWords() : List<Word> {
        return listOf(
            Word(0, "Bienvenue", "مرحبا", Dialect.BEDOUIN, "mar-haban"),
            Word(0, "Maison", "", Dialect.MODERN,""),
            Word(0, "Arbre", "", Dialect.MODERN,""),
            Word(0, "Abricot", "", Dialect.MODERN,""),
            Word(0, "Abrit", "", Dialect.MODERN,""),
            Word(0, "Colonne", "", Dialect.MODERN,""),
            Word(0, "Francais", "", Dialect.MODERN,""),
            Word(0, "Transit", "", Dialect.MODERN,""),
            Word(0, "Transport", "", Dialect.MODERN,""),
            Word(0, "Portable", "", Dialect.MODERN,""),
            Word(0, "Portée", "", Dialect.MODERN,""),
            Word(0, "Prisonnier", "", Dialect.MODERN,""),
            Word(0, "Liberté", "", Dialect.MODERN,""),
            Word(0, "Renouveau", "", Dialect.MODERN,""),
            Word(0, "Bonheur", "", Dialect.MODERN,""),
            Word(0, "Paris", "", Dialect.MODERN,""),
            Word(0, "Vivre", "", Dialect.MODERN,""),
            Word(0, "Ordinateur", "", Dialect.MODERN,""),
        )
    }

    fun addWord(word: Word) {
        database.wordDao().insertOne(word.toEntity())
    }

    fun removeWord(word: Word) {
        database.wordDao().delete(word.toEntity())
    }
}