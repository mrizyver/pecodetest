package com.izyver.pecodetest.toastshower

import android.content.Context

abstract class NavigationToastShower(
    protected val context: Context,
    protected val duration: Int,
    protected val deletedPattern: String,
    protected val createdPattern: String
){
    abstract fun createdScreen(number: Int)

    abstract fun deletedScreen(number: Int)
}