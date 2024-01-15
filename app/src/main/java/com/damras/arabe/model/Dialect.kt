package com.damras.arabe.model

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toDialect(value: String) = enumValueOf<Dialect>(value)

    @TypeConverter
    fun fromDialect(value: Dialect) = value.name
}

enum class Dialect {
    MODERN,
    MAGHREB,
    BEDOUIN,
    EGYPTIAN,
    MIDDLE_EAST,
    ARABIC_PENINSULA
}