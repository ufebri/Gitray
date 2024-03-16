package febri.uray.bedboy.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(

    @PrimaryKey
    @ColumnInfo("user_id")
    var userID: Int? = null,

    @ColumnInfo("user_username")
    var userUsername: String? = null,

    @ColumnInfo("user_avatar")
    var userAvatar: String? = null,

    @ColumnInfo("user_bio")
    var userBio: String? = null,

    @ColumnInfo("user_fullName")
    var userFullName: String? = null,

    @ColumnInfo("user_followers")
    var userFollowers: Int? = null,

    @ColumnInfo("user_following")
    var userFollowing: Int? = null,

    @ColumnInfo("last_visit")
    var lastVisit: String? = null
)
