package com.damras.arabe.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damras.arabe.data.database.entity.Word

@Dao
interface WordDao {
        @Query("SELECT * FROM word")
        fun getAll(): List<Word>

        @Insert
        fun insertOne(word: Word)

        @Delete
        fun delete(word: Word)
}