package com.uray.gitray.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.uray.gitray.databinding.ItemUserBinding
import febri.uray.bedboy.core.domain.model.User

class UserPagingAdapter(private val onClick: (User) -> Unit, private val length: (Int) -> Unit) :
    PagingDataAdapter<User, UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val mData = getItem(position)
        if (mData != null)
            itemCount.let { holder.bind(mData, onClick) }
        else
            itemCount.let { length }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.userID == newItem.userID
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}