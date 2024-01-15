package com.damras.arabe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.damras.arabe.data.database.dao.WordDao
import com.damras.arabe.data.database.entity.Converters
import com.damras.arabe.data.database.entity.Word

@Database(entities = [Word::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "ArabeDb"
    }

    abstract fun wordDao(): WordDao
}
