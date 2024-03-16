package febri.uray.bedboy.core.domain.repository

import androidx.paging.PagingData
import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.domain.model.User
import kotlinx.coroutines.flow.Flow


interface IAppRepository {

    fun insertNewUser(newUser: User)

    fun getUser(username: String): Flow<Resource<User>>

    fun getLastVisits(): Flow<List<User>>

    fun getSearchList(keyword: String): Flow<PagingData<User>>

    fun updateUser(user: User)

    fun deleteUser(user: User)

    fun getFollowers(username: String): Flow<Resource<List<User>>>

    fun getFollowing(username: String): Flow<Resource<List<User>>>

}