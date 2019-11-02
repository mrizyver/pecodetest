package com.izyver.pecodetest.toastshower

import android.content.Context
import android.widget.Toast

/**
 * it works stable but looks like bad
 */
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

    override fun show(pattern: String, number: Int) {
        toast?.cancel()
        toast = Toast.makeText(context, String.format(pattern, number), duration)
        toast?.show()
    }
}