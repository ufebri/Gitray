package febri.uray.bedboy.core.data.source.remote

import android.util.Log
import febri.uray.bedboy.core.data.source.remote.network.ApiResponse
import febri.uray.bedboy.core.data.source.remote.network.ApiService
import febri.uray.bedboy.core.data.source.remote.response.DataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    val TAG = "RemoteDataSource"
    val client = apiService

    suspend fun getDetailUser(selectedUsername: String): Flow<ApiResponse<DataResponse>> {
        return flow {
            val response = apiService.getDetailUser(selectedUsername)
            try {
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error("error"))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowers(selectedUsername: String): Flow<ApiResponse<List<DataResponse>>> {
        return flow {
            val response = apiService.getUserFollowers(selectedUsername)
            try {
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowing(selectedUsername: String): Flow<ApiResponse<List<DataResponse>>> {
        return flow {
            val response = apiService.getUserFollowing(selectedUsername)
            try {
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

