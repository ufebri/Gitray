package com.uray.gitray.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.uray.gitray.R
import com.uray.gitray.databinding.FragmentHomeBinding
import com.uray.gitray.presentation.detailuser.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint
import febri.uray.bedboy.core.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            binding?.apply {
                searchView.setupWithSearchBar(searchBar)
                searchView
                    .editText
                    .setOnEditorActionListener { _, _, _ ->
                        if (searchView.text.toString().isNotEmpty()) {
                            searchBar.text = searchView.text
                            searchView.hide()
                            getListSearchData(searchView.text.toString())
                        }
                        false
                    }
            }

            //Check Last Visit
            homeViewModel.lastVisitList.observe(viewLifecycleOwner) { mData ->
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
    }

    private fun getListSearchData(keyword: String) {
        lifecycleScope.launch {
            showLottieNotFound(false)
            showLoading(true)
            showRecyclerView(false)
            delay(3000L)

            homeViewModel.getSearchUser(keyword).observe(viewLifecycleOwner) { mData ->
                if (mData != null) {
                    binding?.apply {
                        tvTitle.text = getString(R.string.heres_the_result_data)
                        rvListUser.layoutManager = LinearLayoutManager(requireActivity())
                        val mAdapter = UserPagingAdapter(onClick = { onClickData ->
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    DetailUserActivity::class.java
                                ).putExtra("username", onClickData.userUsername)
                            )
                        }, length = { length -> showLottieNotFound(length < 1) })

                        rvListUser.adapter =
                            mAdapter.withLoadStateHeaderAndFooter(footer = LoadingStateAdapter {
                                mAdapter.retry()
                            }, header = LoadingStateAdapter { mAdapter.retry() })

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
    }

    private fun showListData(mDataList: List<User>) {
        binding?.rvListUser?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            val userAdapter = UserAdapter {
                startActivity(
                    Intent(
                        requireActivity(),
                        DetailUserActivity::class.java
                    ).putExtra("username", it.userUsername)
                )
            }
            adapter = userAdapter
            userAdapter.submitList(mDataList)

            binding?.tvTitle?.text = getString(R.string.last_visit_profile)

            showRecyclerView(true)
        }
    }

    private fun showRecyclerView(state: Boolean) {
        binding?.rvListUser?.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding?.progress?.visibility = if (state) View.VISIBLE else View.GONE
        binding?.tvTitle?.visibility = if (state) View.GONE else View.VISIBLE
    }

    private fun showLottieNotFound(state: Boolean) {
        binding?.lottieNotFound?.apply {
            visibility = if (state) View.VISIBLE else View.GONE
            enableMergePathsForKitKatAndAbove(state)
        }
        binding?.tvTitle?.isGone = !state
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}