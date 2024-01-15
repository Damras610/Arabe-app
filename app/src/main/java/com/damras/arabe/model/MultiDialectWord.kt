package com.damras.arabe.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.damras.arabe.data.database.entity.Word as WordEntity

data class MultiDialectWord(
    val french: String,
    val words: List<Word>
)