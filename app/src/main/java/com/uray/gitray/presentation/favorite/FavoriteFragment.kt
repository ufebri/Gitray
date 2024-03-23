package com.uray.gitray.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.uray.gitray.databinding.FragmentFavoriteBinding
import com.uray.gitray.presentation.detailuser.DetailUserActivity
import com.uray.gitray.presentation.home.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            binding?.apply {

                favoriteViewModel.favoriteList.observe(viewLifecycleOwner) { mData ->
                    if (!mData.isNullOrEmpty()) {
                        rvListUser.apply {
                            layoutManager = LinearLayoutManager(requireActivity())
                            val mAdapter = UserAdapter {
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        DetailUserActivity::class.java
                                    ).putExtra("username", it.userUsername)
                                )
                            }
                            adapter = mAdapter
                            mAdapter.submitList(mData)
                        }
                    } else {
                        lottieNotFound.isVisible = true
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}