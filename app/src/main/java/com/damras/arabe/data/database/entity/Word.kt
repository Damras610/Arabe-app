package com.damras.arabe.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.damras.arabe.model.Dialect

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "french") val french: String,
    @ColumnInfo(name = "arabic") val arabic: String,
    @ColumnInfo(name = "dialect") val dialect: Dialect,
    @ColumnInfo(name = "transcription") val transcription: String?
)

class Converters {
    @TypeConverter
    fun toDialect(value: String) = enumValueOf<Dialect>(value)

    @TypeConverter
    fun fromDialect(value: Dialect) = value.name
}