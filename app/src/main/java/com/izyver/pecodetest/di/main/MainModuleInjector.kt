package com.izyver.pecodetest.di.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.izyver.pecodetest.NotificationCreator
import com.izyver.pecodetest.ScreenController

class MainModuleInjector(
    private val screenController: ScreenController,
    private val notificationCreator: NotificationCreator
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        (f as? RequireScreenController)?.setScreenController(screenController)
        (f as? RequireNotificationCreator)?.setNotificatonCreator(notificationCreator)
    }
}