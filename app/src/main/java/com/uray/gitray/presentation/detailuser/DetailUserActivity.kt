package com.uray.gitray.presentation.detailuser

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.uray.gitray.R
import com.uray.gitray.databinding.ActivityDetailUserBinding
import dagger.hilt.android.AndroidEntryPoint
import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.domain.model.User

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username: String = intent.getStringExtra("username") ?: ""

        //show header
        detailUserViewModel.getDetailUser(username).observe(this) { mData ->
            when (mData) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    mData.data.let {
                        binding.apply {
                            Glide.with(this@DetailUserActivity).load(it?.userAvatar).into(sifAvatar)
                            tvBio.text = it?.userBio
                            tvFollowers.text =
                                String.format(
                                    "%s \n %s",
                                    getString(R.string.item_tab_follower),
                                    it?.userFollowers.toString()
                                )
                            tvFollowing.text = String.format(
                                "%s \n %s",
                                getString(R.string.item_tab_following),
                                it?.userFollowing.toString()
                            )
                            tvUsername.text = String.format("@%s", it?.userUsername)
                            tvFullName.text = it?.userFullName
                            showLoading(false)


                            //setFavorite
                            if (it?.isFavorite == true) {
                                fabFavorite.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        this@DetailUserActivity,
                                        R.drawable.baseline_favorite_24
                                    )
                                )
                            } else {
                                fabFavorite.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        this@DetailUserActivity,
                                        R.drawable.baseline_favorite_border_24
                                    )
                                )
                            }

                            fabFavorite.apply {
                                isVisible = true
                                setOnClickListener {
                                    mData.data?.let { data ->
                                        detailUserViewModel.insertFavoriteUser(
                                            data,
                                            !data.isFavorite
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(this, mData.message, Toast.LENGTH_LONG)
                        .show()
                    showLoading(false)
                    finish()
                }
            }
        }

        //setup tablayout
        binding.apply {

            if (username != null) {
                val listTabFragment =
                    arrayListOf(
                        FollowingFragment.newInstance(username),
                        FollowersFragment.newInstance(username)
                    )

                viewPager.adapter = SectionsPagerAdapter(this@DetailUserActivity, listTabFragment)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
                supportActionBar?.elevation = 0f
            }
        }
    }

    private fun setFavorite(user: User, newState: Boolean) =
        detailUserViewModel.insertFavoriteUser(user, newState)

    private fun showLoading(state: Boolean) {
        binding.apply {
            cvSectionProfile.isVisible = !state
            tabs.isVisible = !state
            viewPager.isVisible = !state
            progress.isVisible = state
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.item_tab_following,
            R.string.item_tab_follower
        )
    }

}