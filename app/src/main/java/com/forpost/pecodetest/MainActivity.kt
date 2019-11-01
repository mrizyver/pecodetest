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
            adapter.add(NotificationFragment(screenCount++))
        }

        override fun remove() {
            if (screenCount == 0) return
            adapter.remove()
            screenCount--
        }
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
            fragments.removeAt(fragments.size - 1)
            notifyDataSetChanged()
        }
    }
}
