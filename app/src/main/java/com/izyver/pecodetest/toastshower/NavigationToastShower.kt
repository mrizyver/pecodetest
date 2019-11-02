package com.izyver.pecodetest.toastshower

import android.content.Context

abstract class NavigationToastShower(
    protected val context: Context,
    protected val duration: Int,
    protected val deletedPattern: String,
    protected val createdPattern: String
) {

    constructor(
        context: Context, duration: Int,
        deletedPattern: Int, createdPattern: Int
    ) : this(
        context, duration,
        context.getString(deletedPattern),
        context.getString(createdPattern)
    )

    open fun createdScreen(number: Int){
        show(createdPattern, number)
    }

    open fun deletedScreen(number: Int){
        show(deletedPattern, number)
    }

    protected open fun show(pattern:String, number: Int){
        throw UnsupportedOperationException("You must override this method " +
                "if you don't override createdScreen()/deletedScreen()")
    }
}