package com.izyver.pecodetest.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ScreenControllerInjector(private val screenController: ScreenController)
    : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        (f as? RequireScreenController)?.setScreenController(screenController)
    }
}