package febri.uray.bedboy.core.data.source.local

import febri.uray.bedboy.core.data.source.local.entity.UserEntity
import febri.uray.bedboy.core.data.source.local.room.AppDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val appDao: AppDao) {

    fun insertNewUser(userData: UserEntity) = appDao.insertNewUser(userData)

    fun getUserData(username: String): Flow<UserEntity> = appDao.getUser(username)

    fun getLastVisit(): Flow<List<UserEntity>> = appDao.getLastVisit()

    fun updateUser(user: UserEntity) = appDao.updateUser(user)

    fun deleteUser(user: UserEntity) = appDao.deleteUser(user)

    fun getFavoriteUsers(): Flow<List<UserEntity>> = appDao.getFavoriteUser()

    fun insertFavoriteUser(userData: UserEntity, newState: Boolean) {
        userData.isFavorite = newState
        updateUser(userData)
    }

}