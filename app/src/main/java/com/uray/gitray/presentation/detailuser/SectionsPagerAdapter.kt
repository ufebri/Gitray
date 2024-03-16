package com.uray.gitray.presentation.detailuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(
    activity: AppCompatActivity,
    private val content: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            position -> content[position]
            else -> content[0]
        }

        return fragment
    }

    override fun getItemCount(): Int = content.size
}