package com.example.ridecellexample.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.ridecellexample.data.model.CharacterData

@Dao
interface CharacterDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCharacters(vararg messageData: CharacterData)

    @Query("SELECT * FROM CharacterData ORDER BY id DESC")
    suspend fun getAllCharacter(): List<CharacterData>

    @Query("SELECT * FROM CharacterData  WHERE name LIKE :name")
    suspend fun searchCharacter(name: String): List<CharacterData>

    @Query("DELETE FROM CharacterData WHERE  id IN (:id)")
    suspend fun deleteCharacterData(id: String)

}
