package febri.uray.bedboy.core.domain.usecase

import androidx.paging.PagingData
import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppInteractor @Inject constructor(private var appRepository: IAppRepository) :
    AppUseCase {

    override fun insertNewUser(user: User) = appRepository.insertNewUser(user)

    override fun getUser(username: String): Flow<Resource<User>> = appRepository.getUser(username)

    override fun getSearchListUser(keyword: String): Flow<PagingData<User>> =
        appRepository.getSearchList(keyword)

    override fun getLastVisit(): Flow<List<User>> = appRepository.getLastVisits()

    override fun updateUser(user: User) = appRepository.updateUser(user)

    override fun deleteUser(user: User) = appRepository.deleteUser(user)

    override fun getFollowers(username: String): Flow<Resource<List<User>>> =
        appRepository.getFollowers(username)

    override fun getFollowing(username: String): Flow<Resource<List<User>>> =
        appRepository.getFollowing(username)

    override fun getFavoriteUsers(): Flow<List<User>> = appRepository.getListFavoriteUsers()

    override fun insertFavoriteUser(user: User, newState: Boolean) =
        appRepository.insertFavoriteUser(user, newState)

    override fun getBooleanPreferenceKey(key: String): Flow<Boolean> =
        appRepository.getBooleanPreferenceKey(key)

    override fun saveBooleanPreferenceKey(key: String, newState: Boolean) =
        appRepository.saveBooleanPreferenceKey(key, newState)

}