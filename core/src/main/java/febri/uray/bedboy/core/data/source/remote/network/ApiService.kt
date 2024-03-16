package febri.uray.bedboy.core.data.source.remote.network

import febri.uray.bedboy.core.data.source.remote.response.DataResponse
import febri.uray.bedboy.core.data.source.remote.response.Responses
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): Responses

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
    ): DataResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String,
    ): List<DataResponse>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
    ): List<DataResponse>
}