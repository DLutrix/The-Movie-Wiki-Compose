package com.dlutrix.themoviewikicompose.util

import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (_: IOException) {
            query().map { Resource.Error(it, Throwable("IO error")) }
        } catch (_: HttpException) {
            query().map { Resource.Error(it, Throwable("No internet connection")) }
        } catch (_: Exception) {
            query().map { Resource.Error(it, Throwable("Unexpected error")) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}