package com.josua0056.batarang.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geometri")
data class Geometri(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val deskripsi: String,
    val jenis: String, // Bangun Datar atau Bangun Ruang
    val rumus: String
)
