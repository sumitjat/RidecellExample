package com.example.ridecellexample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ridecellexample.data.model.CharacterData

@Database(
    entities = [CharacterData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}