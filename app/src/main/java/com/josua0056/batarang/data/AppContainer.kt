package com.josua0056.batarang.data

import android.content.Context
import com.josua0056.batarang.model.GeometriDatabase
import com.josua0056.batarang.model.GeometriRepository
import com.josua0056.batarang.model.OfflineGeometriRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val geometriRepository: GeometriRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineGeometriRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [GeometriRepository]
     */
    override val geometriRepository: GeometriRepository by lazy {
        OfflineGeometriRepository(GeometriDatabase.getDatabase(context).geometriDao())
    }
}
