package com.izyver.pecodetest.di.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.izyver.pecodetest.ScreenController
import com.izyver.pecodetest.notifications.NotificationCreator

class MainModuleInjector(
    private val screenController: ScreenController,
    private val notificationCreator: NotificationCreator
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        (f as? RequireScreenController)?.setScreenController(screenController)
        (f as? RequireNotificationCreator)?.setNotificationCreator(notificationCreator)
    }
}