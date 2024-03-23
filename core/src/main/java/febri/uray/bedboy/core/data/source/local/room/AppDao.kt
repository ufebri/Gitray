package febri.uray.bedboy.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import febri.uray.bedboy.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewUser(entity: UserEntity)

    @Query("SELECT * FROM user_entity WHERE user_username = :userName")
    fun getUser(userName: String): Flow<UserEntity>

    @Query("SELECT * FROM user_entity ORDER BY last_visit DESC")
    fun getLastVisit(): Flow<List<UserEntity>>

    @Update
    fun updateUser(mUser: UserEntity)

    @Delete
    fun deleteUser(mUser: UserEntity)

    @Query("SELECT * FROM user_entity WHERE isFavorite = 1")
    fun getFavoriteUser(): Flow<List<UserEntity>>
}