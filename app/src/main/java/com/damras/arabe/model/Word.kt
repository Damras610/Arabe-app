package com.damras.arabe.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.damras.arabe.data.database.entity.Word as WordEntity

data class Word(
    val uid: Long = 0,
    val french: String,
    val arabic: String,
    val dialect: Dialect,
    val transcription: String?
) {
    fun toEntity() : WordEntity {
        return WordEntity(uid, french, arabic, dialect, transcription)
    }
}

fun WordEntity.toModel(): Word {
    return Word(uid, french, arabic, dialect, transcription)
}