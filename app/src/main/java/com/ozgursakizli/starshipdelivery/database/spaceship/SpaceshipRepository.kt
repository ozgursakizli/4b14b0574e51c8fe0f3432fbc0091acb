package com.ozgursakizli.starshipdelivery.database.spaceship

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SpaceshipRepository @Inject constructor(private val spaceshipDao: SpaceshipDao) : SpaceshipDataSource {

    override fun getSpaceship(): Flow<SpaceshipEntity?> {
        return spaceshipDao.getSpaceship()
    }

    override suspend fun insert(spaceshipEntity: SpaceshipEntity): Long {
        return spaceshipDao.insert(spaceshipEntity)
    }

    override suspend fun update(spaceshipEntity: SpaceshipEntity): Int {
        return spaceshipDao.update(spaceshipEntity)
    }

    override suspend fun delete(spaceshipEntity: SpaceshipEntity) {
        spaceshipDao.delete(spaceshipEntity)
    }

}