package com.ozgursakizli.starshipdelivery.network.generic

import kotlinx.coroutines.Dispatchers

open class BaseRepository {
    protected val dispatcher = Dispatchers.IO
}