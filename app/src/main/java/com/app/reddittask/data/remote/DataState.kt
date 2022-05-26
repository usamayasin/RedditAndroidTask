package com.app.reddittask.data.remote


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: String) : DataState<T>()

    companion object {
        fun <T> success(data: T) = Success<T>(data)
        fun <T> error(message: String) = Error<T>(message)
    }

    sealed class CustomMessages(val message: String = "") {

        object Timeout : CustomMessages()
        object EmptyData : CustomMessages()
        object ServerBusy : CustomMessages()
        object HttpException : CustomMessages()
        object SocketTimeOutException : CustomMessages()
        object NoInternet : CustomMessages()
        object Unauthorized : CustomMessages()
        object InternalServerError : CustomMessages()
        object BadRequest : CustomMessages()
        object Conflict : CustomMessages()
        object NotFound : CustomMessages()
        object NotAcceptable : CustomMessages()
        object ServiceUnavailable : CustomMessages()
        object Forbidden : CustomMessages()
        data class SomethingWentWrong(val error: String) : CustomMessages(message = error)
    }

}
