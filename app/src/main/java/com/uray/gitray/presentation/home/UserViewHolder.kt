package com.uray.gitray.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uray.gitray.databinding.ItemUserBinding
import febri.uray.bedboy.core.domain.model.User

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: User, onClick: (User) -> Unit) {
        binding.apply {

            //avatar
            Glide.with(binding.root).load(mData.userAvatar).into(binding.sifAvatar)

            //username
            tvUsername.text = String.format("@%s", mData.userUsername)

            //Fullname
            tvFullName.text = mData.userFullName

            root.setOnClickListener { onClick(mData) }
        }
    }
}