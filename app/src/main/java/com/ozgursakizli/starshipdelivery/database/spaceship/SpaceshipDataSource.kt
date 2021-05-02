package com.ozgursakizli.starshipdelivery.database.spaceship

import kotlinx.coroutines.flow.Flow

interface SpaceshipDataSource {
    fun getSpaceship(): Flow<SpaceshipEntity?>
    suspend fun insert(spaceshipEntity: SpaceshipEntity): Long
    suspend fun update(spaceshipEntity: SpaceshipEntity): Int
}