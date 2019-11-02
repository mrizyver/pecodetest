package com.izyver.pecodetest.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class NotificationFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    private val fragments = ArrayList<Fragment>()

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getItemPosition(fragment: Any): Int {
        val index = fragments.indexOf(fragment)
        if( index < 0) return PagerAdapter.POSITION_NONE
        return index
    }

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