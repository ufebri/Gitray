package febri.uray.bedboy.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import febri.uray.bedboy.core.data.source.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}