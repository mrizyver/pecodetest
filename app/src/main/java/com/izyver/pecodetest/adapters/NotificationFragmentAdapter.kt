package com.izyver.pecodetest.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class NotificationFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    private val fragments = ArrayList<Fragment>()

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    fun add(fragment: Fragment) {
        fragments.add(fragment)
        notifyDataSetChanged()
    }

    fun remove() {
        if (fragments.isEmpty()) return
        fragments.removeAt(fragments.size - 1)
        notifyDataSetChanged()
    }
}