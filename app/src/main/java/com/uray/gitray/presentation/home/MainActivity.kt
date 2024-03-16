package com.uray.gitray.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.uray.gitray.R
import com.uray.gitray.databinding.ActivityMainBinding
import com.uray.gitray.presentation.detailuser.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint
import febri.uray.bedboy.core.domain.model.User

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    if (searchView.text.isNotEmpty()) {
                        searchBar.setText(searchView.text)
                        searchView.hide()
                        getListSearchData(searchView.text.toString())
                    }
                    false
                }
        }

        //Check Last Visit
        homeViewModel.lastVisitList.observe(this) { mData ->
            showLoading(true)
            if (mData.isNullOrEmpty()) {
                showLoading(false)
                showLottieNotFound(true)
            } else {
                showLottieNotFound(false)
                showListData(mData)
                showLoading(false)
            }
        }
    }

    private fun getListSearchData(keyword: String) {
        showLottieNotFound(false)
        showRecyclerView(false)

        homeViewModel.getSearchUser(keyword).observe(this) { mData ->
            if (mData != null) {
                binding.rvListUser.apply {
                    showLoading(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    val mAdapter = UserPagingAdapter(onClick = { onClickData ->
                        startActivity(
                            Intent(
                                this@MainActivity,
                                DetailUserActivity::class.java
                            ).putExtra("username", onClickData.userUsername)
                        )
                    }, length = { length -> showLottieNotFound(length < 1) })

                    adapter = mAdapter.withLoadStateFooter(footer = LoadingStateAdapter {
                        mAdapter.retry()
                    })

                    binding.tvTitle.text = getString(R.string.heres_the_result_data)

                    mAdapter.submitData(lifecycle, mData)

                    showLoading(false)
                    showRecyclerView(true)
                }
            } else {
                showLoading(false)
                showLottieNotFound(true)
            }
        }
    }

    private fun showListData(mDataList: List<User>) {
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val userAdapter = UserAdapter {
                startActivity(
                    Intent(
                        this@MainActivity,
                        DetailUserActivity::class.java
                    ).putExtra("username", it.userUsername)
                )
            }
            adapter = userAdapter
            userAdapter.submitList(mDataList)

            binding.tvTitle.text = getString(R.string.last_visit_profile)

            showRecyclerView(true)
        }
    }

    private fun showRecyclerView(state: Boolean) {
        binding.rvListUser.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.progress.visibility = if (state) View.VISIBLE else View.GONE
        binding.tvTitle.visibility = if (state) View.GONE else View.VISIBLE
    }

    private fun showLottieNotFound(state: Boolean) {
        binding.lottieNotFound.apply {
            visibility = if (state) View.VISIBLE else View.GONE
            enableMergePathsForKitKatAndAbove(state)
        }
        binding.tvTitle.isGone = !state
    }
}