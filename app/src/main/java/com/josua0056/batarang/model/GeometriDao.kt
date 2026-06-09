package com.josua0056.batarang.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GeometriDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(geometri: Geometri)

    @Update
    suspend fun update(geometri: Geometri)

    @Delete
    suspend fun delete(geometri: Geometri)

    @Query("SELECT * from geometri ORDER BY nama ASC")
    fun getAllGeometri(): Flow<List<Geometri>>

    @Query("SELECT * from geometri WHERE id = :id")
    fun getGeometri(id: Int): Flow<Geometri>
}
