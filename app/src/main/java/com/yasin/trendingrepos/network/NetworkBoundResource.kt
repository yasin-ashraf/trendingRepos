package com.yasin.trendingrepos.network

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread protected constructor() {
    private val result: MediatorLiveData<NetworkState<ResultType>> =
        MediatorLiveData<NetworkState<ResultType>>()

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        result.addSource(dbSource) {
            result.value = NetworkState.Loading
        }
        createCall().enqueue(object : Callback<RequestType> {
            override fun onResponse(
                call: Call<RequestType>,
                response: Response<RequestType>
            ) {
                result.removeSource(dbSource)
                if (response.isSuccessful) {
                    saveResultAndReInit(response.body())
                } else {
                    onFetchFailed()
                }
            }

            override fun onFailure(call: Call<RequestType>, t: Throwable) {
                Log.e("ERROR FETCHING DATA", t.toString())
                onFetchFailed()
                result.removeSource(dbSource)
                result.addSource(dbSource) {
                    result.value = NetworkState.Error(t.message ?: "")
                }
            }
        })
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private fun saveResultAndReInit(response: RequestType?) {
        object : AsyncTask<Unit?, Unit?, Unit?>() {

            override fun onPostExecute(aVoid: Unit?) {
                result.addSource(loadFromDb()) { newData: ResultType ->
                    result.value = NetworkState.Success(newData)
                }
            }

            override fun doInBackground(vararg p0: Unit?): Unit? {
                saveCallResult(response)
                return Unit
            }
        }.execute()
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType?)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): Call<RequestType>

    @MainThread
    protected abstract fun onFetchFailed()
    val asLiveData: LiveData<NetworkState<ResultType>>
        get() = result

    init {
        result.value = NetworkState.Loading
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data: ResultType ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(
                    dbSource
                ) { newData: ResultType ->
                    result.value = NetworkState.Success(newData)
                }
            }
        }
    }
}