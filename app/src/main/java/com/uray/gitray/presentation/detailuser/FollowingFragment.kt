package com.uray.gitray.presentation.detailuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.uray.gitray.databinding.FragmentFollowingBinding
import com.uray.gitray.presentation.home.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import febri.uray.bedboy.core.data.Resource

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private val detailUserViewModel: DetailUserViewModel by viewModels()
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_TEXT) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            binding?.apply {
                detailUserViewModel.getFollowingData(username)
                    .observe(viewLifecycleOwner) { mData ->
                        when (mData) {
                            is Resource.Loading -> binding?.progress?.isVisible = true
                            is Resource.Success -> {
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

                                    mAdapter.submitList(mData.data)
                                    binding?.progress?.isGone = true
                                }
                            }

                            is Resource.Error -> {
                                Toast.makeText(requireActivity(), mData.message, Toast.LENGTH_LONG)
                                    .show()
                                binding?.progress?.isGone = true
                                requireActivity().finish()
                            }
                        }
                    }
            }
        }
    }

    companion object {
        private const val ARG_TEXT = "fragmentTAG"

        fun newInstance(text: String) =
            FollowingFragment().apply {
                arguments =
                    Bundle().apply {
                        putString(ARG_TEXT, text)
                    }
            }
    }
}