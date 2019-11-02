package com.izyver.pecodetest.toastshower

import android.content.Context
import android.widget.Toast

class SimpleToastShower(
    context: Context, duration: Int,
    deletedPattern: Int, createdPattern: Int
) :
    NavigationToastShower(
        context, duration,
        context.getString(deletedPattern),
        context.getString(createdPattern)
    ) {

    private var toast: Toast? = null

    override fun createdScreen(number: Int) {
        show(createdPattern, number)
    }

    override fun deletedScreen(number: Int) {
        show(deletedPattern, number)
    }

    private fun show(pattern: String, number: Int) {
        toast?.cancel()
        toast = Toast.makeText(context, String.format(pattern, number), duration)
        toast?.show()
    }
}