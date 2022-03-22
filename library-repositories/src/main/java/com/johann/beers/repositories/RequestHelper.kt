package com.johann.beers.repositories

import android.util.Log
import kotlinx.coroutines.withTimeout
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.HttpException

class RequestHelper(private val connectionService: ConnectionService) {
    suspend fun <T> tryRequest(
        timeout: Long? = null,
        fallback: (() -> T)? = null,
        request: suspend () -> T
    ): DataResult<out T> = try {
        if (timeout != null) {
            withTimeout(timeout) { DataResult.Success.NetworkSuccess(request()) }
        } else {
            DataResult.Success.NetworkSuccess(request())
        }
    } catch (err: Exception) {
        val fallbackResult = try {
            val data = fallback!!()
            DataResult.Success.NetworkSuccess(data)
        } catch (err: Exception) {
            null
        }

        if (fallbackResult != null) {
            Log.i("RequestHelper","Request failed, returning fallback instead")
            fallbackResult
        } else {
            when (err) {
                is HttpException,
                is ConnectionShutdownException -> DataResult.Error.AppError(err)
                else -> if (!connectionService.isConnectedToInternet()) {
                    DataResult.Error.NoNetwork(err)
                } else DataResult.Error.AppError(err)
            }.also {
                if (it is DataResult.Error.AppError) {
                    System.out.println("Request failed"+err.localizedMessage)
                } else {
                    Log.i("RequestHelper","Request failed, client has no network (${err::class.simpleName})")
                }
            }
        }
    }
}