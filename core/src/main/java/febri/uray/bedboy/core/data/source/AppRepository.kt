package febri.uray.bedboy.core.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import febri.uray.bedboy.core.data.NetworkBoundResource
import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.data.source.local.LocalDataSource
import febri.uray.bedboy.core.data.source.remote.AppPagingSource
import febri.uray.bedboy.core.data.source.remote.RemoteDataSource
import febri.uray.bedboy.core.data.source.remote.network.ApiResponse
import febri.uray.bedboy.core.data.source.remote.response.DataResponse
import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.IAppRepository
import febri.uray.bedboy.core.util.AppExecutors
import febri.uray.bedboy.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val dataStore: DataStore<Preferences>
) : IAppRepository {

    override fun insertNewUser(newUser: User) {
        return appExecutors.diskIO()
            .execute { localDataSource.insertNewUser(DataMapper.mapDomainToUserEntities(newUser)) }
    }

    override fun getUser(username: String): Flow<Resource<User>> =
        object : NetworkBoundResource<User, DataResponse>() {
            override fun loadFromDB(): Flow<User> {
                return localDataSource.getUserData(username)
                    .map { DataMapper.mapEntitiesToUserDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<DataResponse>> =
                remoteDataSource.getDetailUser(username)

            override suspend fun saveCallResult(data: DataResponse) {
                val mData = DataMapper.mapResponseToEntities(data)
                appExecutors.diskIO().execute { localDataSource.insertNewUser(mData) }
            }

            override fun shouldFetch(data: User?): Boolean = data?.userUsername.isNullOrEmpty()

        }.asFlow()

    override fun getLastVisits(): Flow<List<User>> {
        return localDataSource.getLastVisit().map { DataMapper.mapEntitiesToUsersDomain(it) }
    }

    override fun getSearchList(keyword: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(30),
            pagingSourceFactory = {
                AppPagingSource(remoteDataSource.client, keyword)
            }
        ).flow
    }


    override fun updateUser(user: User) {
        val mUser = DataMapper.mapDomainToUserEntities(user)
        return appExecutors.diskIO().execute { localDataSource.updateUser(mUser) }
    }

    override fun deleteUser(user: User) {
        val mUser = DataMapper.mapDomainToUserEntities(user)
        return appExecutors.diskIO().execute { localDataSource.deleteUser(mUser) }
    }

    override fun getFollowers(username: String): Flow<Resource<List<User>>> = flow {
        remoteDataSource.getUserFollowers(username).collect {
            emit(Resource.Loading())
            when (it) {
                is ApiResponse.Success -> emit(
                    Resource.Success(
                        DataMapper.mapResponseToUsersDomain(
                            it.data
                        )
                    )
                )

                is ApiResponse.Error -> emit(Resource.Error("error"))
                is ApiResponse.Empty -> emit(Resource.Error("empty"))
            }
        }
    }

    override fun getFollowing(username: String): Flow<Resource<List<User>>> =
        flow {
            remoteDataSource.getUserFollowing(username).collect {
                emit(Resource.Loading())
                when (it) {
                    is ApiResponse.Success -> emit(
                        Resource.Success(
                            DataMapper.mapResponseToUsersDomain(
                                it.data
                            )
                        )
                    )

                    is ApiResponse.Error -> emit(Resource.Error("error"))
                    is ApiResponse.Empty -> emit(Resource.Error("empty"))
                }
            }
        }


    override fun getListFavoriteUsers(): Flow<List<User>> =
        localDataSource.getFavoriteUsers().map { DataMapper.mapEntitiesToUsersDomain(it) }

    override fun insertFavoriteUser(user: User, newState: Boolean) {
        val mData = DataMapper.mapDomainToUserEntities(user)
        return appExecutors.diskIO().execute { localDataSource.insertFavoriteUser(mData, newState) }
    }

    override fun getBooleanPreferenceKey(key: String): Flow<Boolean> =
        dataStore.data.map { preferences ->
            val mKey = booleanPreferencesKey(key)
            preferences[mKey] ?: false
        }

    override fun saveBooleanPreferenceKey(key: String, newState: Boolean) {
        runBlocking {
            launch {
                dataStore.edit { preferences ->
                    val mKey = booleanPreferencesKey(key)
                    preferences[mKey] = newState
                }
            }
        }
    }
}