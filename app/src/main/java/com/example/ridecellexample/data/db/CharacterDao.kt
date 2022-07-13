package com.example.ridecellexample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.ridecellexample.data.model.CharacterData

@Dao
interface CharacterDao {

    @Insert(onConflict = REPLACE)
    fun insertCharacters(vararg messageData: CharacterData)

    @Query("SELECT * FROM CharacterData ORDER BY id DESC")
    fun getAllCharacter(): List<CharacterData>

    @Query("SELECT * FROM CharacterData  WHERE name LIKE :name")
    fun searchCharacter(name: String): List<CharacterData>

    @Query("DELETE FROM CharacterData WHERE  id IN (:id)")
    fun deleteCharacterData(id: String)

}