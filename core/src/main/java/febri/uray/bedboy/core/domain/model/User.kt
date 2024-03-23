package febri.uray.bedboy.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var userID: Int? = null,
    var userUsername: String? = null,
    var userAvatar: String? = null,
    var userBio: String? = null,
    var userFullName: String? = null,
    var userFollowers: Int? = null,
    var userFollowing: Int? = null,
    var lastVisit: String? = null,
    var isFavorite: Boolean
) : Parcelable
