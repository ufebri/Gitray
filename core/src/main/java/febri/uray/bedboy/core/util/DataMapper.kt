package febri.uray.bedboy.core.util

import febri.uray.bedboy.core.data.source.local.entity.UserEntity
import febri.uray.bedboy.core.data.source.remote.response.DataResponse
import febri.uray.bedboy.core.domain.model.User

object DataMapper {

    fun mapDomainToUserEntities(input: User): UserEntity = input.let {
        UserEntity(
            userID = it.userID,
            userUsername = it.userUsername,
            userAvatar = it.userAvatar,
            userBio = it.userBio,
            userFullName = it.userFullName,
            userFollowers = it.userFollowers,
            userFollowing = it.userFollowing,
            lastVisit = ""
        )
    }

    fun mapEntitiesToUserDomain(input: UserEntity?): User = input.let {
        User(
            it?.userID ?: -1,
            it?.userUsername ?: "",
            it?.userAvatar ?: "",
            it?.userBio ?: "",
            it?.userFullName ?: "",
            it?.userFollowers,
            it?.userFollowing,
            it?.lastVisit
        )
    }

    fun mapEntitiesToUsersDomain(input: List<UserEntity>): List<User> {
        val mListData = ArrayList<User>()
        input.map {
            val user = User(
                it.userID ?: -1,
                it.userUsername,
                it.userAvatar,
                it.userBio,
                it.userFullName,
                it.userFollowers,
                it.userFollowing,
                it.lastVisit
            )
            mListData.add(user)
        }
        return mListData
    }

    fun mapResponseToUserDomain(input: DataResponse): User = input.let {
        User(
            userID = it.id,
            userUsername = it.login,
            userAvatar = it.avatarUrl,
            userFollowing = it.following,
            userFollowers = it.followers,
            userBio = it.bio,
            userFullName = it.name
        )
    }

    fun mapResponseToEntities(input: DataResponse): UserEntity = input.let {
        UserEntity(
            userID = it.id,
            userBio = it.bio,
            userFollowers = it.followers,
            userFollowing = it.following,
            userAvatar = it.avatarUrl,
            userFullName = it.name,
            userUsername = it.login
        )
    }

    fun mapResponseToUsersDomain(input: List<DataResponse>): List<User> {
        val mListData = ArrayList<User>()
        input.map {
            val user = User(
                it.id ?: -1,
                userUsername = it.login,
                userAvatar = it.avatarUrl,
                userBio = it.bio,
                userFullName = it.name,
                userFollowers = it.followers,
                userFollowing = it.following,
                ""
            )
            mListData.add(user)
        }
        return mListData
    }
}