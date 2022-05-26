package com.app.reddittask.data.repository

import androidx.annotation.WorkerThread
import com.app.reddittask.data.remote.ApiService
import com.app.reddittask.data.remote.DataState
import com.app.reddittask.data.model.CharactersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : Repository {

    @WorkerThread
    override suspend fun getCharacters(pageNo: Int): Flow<DataState<CharactersResponse>> = flow {

        apiService.getCharacters(page = pageNo).apply {
            if (this.isSuccessful) {
                body()?.let {
                    emit(DataState.success(body()!!))
                } ?: run {
                    emit(DataState.error<CharactersResponse>(message = "Something Went Wrong!,Try Again"))
                }
            } else {
                this.errorBody()?.let {
                    emit(DataState.error<CharactersResponse>(message ="Something Went Wrong!,Try Again"))
                } ?: run {
                    emit(DataState.error<CharactersResponse>(message = "Contact Support,Thanks"))
                }
            }

        }

    }.catch {
        this.emit(DataState.error<CharactersResponse>(it.message!!))
    }

}
