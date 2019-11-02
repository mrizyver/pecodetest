package com.izyver.pecodetest

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.izyver.pecodetest.adapters.NotificationFragmentAdapter
import com.izyver.pecodetest.di.main.MainModuleInjector
import com.izyver.pecodetest.notifications.MainNotificationCreator
import com.izyver.pecodetest.notifications.MainNotificationCreator.Companion.ACTION_OPEN_SPECIFIC_SCREEN
import com.izyver.pecodetest.notifications.MainNotificationCreator.Companion.KEY_NUMBER_OF_SCREEN
import com.izyver.pecodetest.toastshower.NavigationToastShower
import com.izyver.pecodetest.toastshower.SimpleToastShower
import com.izyver.pecodetest.toastshower.reflect.ReflectToastShower
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NotificationFragmentAdapter
    private lateinit var screenController: MainScreenController

    @RequiresApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NotificationFragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        screenController = MainScreenController()
        screenController.restoreState(savedInstanceState?.getInt(KEY_SCREEN_CONTROLLER_STATE) ?: 1)
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            MainModuleInjector(screenController, MainNotificationCreator(this)), true
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SCREEN_CONTROLLER_STATE, screenController.count)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent ?: return
        if (intent.action == ACTION_OPEN_SPECIFIC_SCREEN) openScreen(intent)
    }

    private fun openScreen(intent: Intent) {
        val extras = intent.extras ?: return
        val number = extras[KEY_NUMBER_OF_SCREEN] as? Int ?: return
        viewPager?.setCurrentItem(number - 1, true)
    }

    inner class MainScreenController : ScreenController {
        private var screenCount = 0
        override val count: Int get() = screenCount
        private val toastShower: NavigationToastShower by lazy {
            val context = this@MainActivity
            val duration = Toast.LENGTH_SHORT
            val deletedPattern = R.string.notification_fragment_deleted_format
            val createdPattern = R.string.notification_fragment_created_format
            if (enableReflectForBetterUI)
                ReflectToastShower(context, duration, deletedPattern, createdPattern)
            else
                SimpleToastShower(context, duration, deletedPattern, createdPattern)
        }

        override fun add() {
            val fragment = supportFragmentManager.fragments.findNotificationFragment(++screenCount)
                ?: NotificationFragment(screenCount)
            adapter.add(fragment)
            if (viewPager.currentItem == screenCount - 2) {
                viewPager.setCurrentItem(screenCount - 1, true)
            } else {
                toastShower.createdScreen(screenCount)
            }
        }

        override fun remove() {
            if (screenCount <= 1) return
            if (viewPager.currentItem == --screenCount) {
                viewPager.setCurrentItem(screenCount - 1, true)
            } else {
                toastShower.deletedScreen(screenCount + 1)
            }
            adapter.remove()
        }

        fun restoreState(number: Int){
            repeat(number){
                add()
            }
        }
    }

    private fun List<Fragment>.findNotificationFragment(number: Int): Fragment? {
        for (fragment in this) {
            val currentName = NotificationFragment.makeFragmentName(number)
            val fragmentName = fragment.arguments?.getString(NotificationFragment.ARG_FRAGMENT_NAME) ?: ""
            if (currentName == fragmentName) return fragment
        }
        return null
    }

    private companion object {
        private val enableReflectForBetterUI = SDK_INT < P && BuildConfig.ENABLE_REFLECTION
        private const val KEY_SCREEN_CONTROLLER_STATE = "screen_controller_state"
    }
}
