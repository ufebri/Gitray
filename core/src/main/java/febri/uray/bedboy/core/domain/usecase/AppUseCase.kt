package febri.uray.bedboy.core.domain.usecase

import androidx.paging.PagingData
import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.domain.model.User
import kotlinx.coroutines.flow.Flow


interface AppUseCase {

    fun insertNewUser(user: User)
    fun getUser(username: String): Flow<Resource<User>>
    fun getSearchListUser(keyword: String): Flow<PagingData<User>>
    fun getLastVisit(): Flow<List<User>>
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun getFollowers(username: String): Flow<Resource<List<User>>>
    fun getFollowing(username: String): Flow<Resource<List<User>>>

}