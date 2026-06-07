package com.example.core_network.utils

import android.util.Log
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

private const val TAG = "Network"
suspend fun <T> safeApiCall(
    apiName: String,
    apiCall: suspend () -> T
): T {
    return try {
        apiCall()
    } catch (e: CancellationException) {
        throw e
    } catch (e: IOException) {
        Log.e(TAG, "$apiName: No internet connection", e)
        throw e
    } catch (e: HttpException) {
        Log.e(TAG, "$apiName: Http ${e.code()}", e)
        throw e
    } catch (e: Exception) {
        Log.e(TAG, "$apiName: Unknown error", e)
        throw e
    }
}
