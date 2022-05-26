package com.app.reddittask.data.repository

import com.app.reddittask.data.remote.DataState
import com.app.reddittask.data.model.CharactersResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [RepositoryImpl] for implementation of this class to utilize APIService.
 */

interface Repository {

    suspend fun getCharacters(page:Int): Flow<DataState<CharactersResponse>>
}
