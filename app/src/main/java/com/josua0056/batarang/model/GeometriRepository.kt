package com.josua0056.batarang.model

import kotlinx.coroutines.flow.Flow

interface GeometriRepository {
    fun getAllGeometriStream(): Flow<List<Geometri>>
    fun getGeometriStream(id: Int): Flow<Geometri?>
    suspend fun insertGeometri(geometri: Geometri)
    suspend fun deleteGeometri(geometri: Geometri)
    suspend fun updateGeometri(geometri: Geometri)
}

class OfflineGeometriRepository(private val geometriDao: GeometriDao) : GeometriRepository {
    override fun getAllGeometriStream(): Flow<List<Geometri>> = geometriDao.getAllGeometri()
    override fun getGeometriStream(id: Int): Flow<Geometri?> = geometriDao.getGeometri(id)
    override suspend fun insertGeometri(geometri: Geometri) = geometriDao.insert(geometri)
    override suspend fun deleteGeometri(geometri: Geometri) = geometriDao.delete(geometri)
    override suspend fun updateGeometri(geometri: Geometri) = geometriDao.update(geometri)
}
