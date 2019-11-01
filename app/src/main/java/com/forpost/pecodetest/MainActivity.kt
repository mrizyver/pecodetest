package com.forpost.pecodetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.forpost.pecodetest.di.ScreenController
import com.forpost.pecodetest.di.ScreenControllerInjector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NotificationFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NotificationFragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            ScreenControllerInjector(MainScreenController()), true
        )
    }

    inner class MainScreenController : ScreenController {
        private var screenCount = 0

        init {
            add()
        }

        override fun add() {
            val fragment = supportFragmentManager.fragments.findNotificationFragment(++screenCount)
                ?: NotificationFragment(screenCount)
            adapter.add(fragment)
            viewPager.setCurrentItem(screenCount - 1, true)
        }

        override fun remove() {
            if (screenCount <= 1) return
            viewPager.setCurrentItem(--screenCount - 1, true)
            adapter.remove()
        }
    }

    private fun List<Fragment>.findNotificationFragment(number: Int): Fragment? {
        for (fragment in this) {
            val currentName = NotificationFragment.makeFragmentName(number)
            val fragmentName = fragment.arguments?.getString(NotificationFragment.ARG_FRAGMENT_NAME) ?: ""
            if (currentName == fragmentName)
                return fragment
        }
        return null
    }

    class NotificationFragmentAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
}
