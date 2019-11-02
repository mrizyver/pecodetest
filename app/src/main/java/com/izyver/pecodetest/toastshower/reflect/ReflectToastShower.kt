package com.izyver.pecodetest.toastshower.reflect

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.MessageQueue
import android.widget.Toast
import com.izyver.pecodetest.toastshower.NavigationToastShower

/**
 * it looks pretty but sometime can no appear
 * Max available API - 27, reflection doesn't work at API-28 and higher
 */
class ReflectToastShower(
    context: Context, duration: Int,
    deletedPattern: Int,
    createdPattern: Int
) : NavigationToastShower(context, duration, deletedPattern, createdPattern) {

    private val toast: Toast
    private val handler: Handler?
    private val messageQueue: MessageQueue?
    private val helper: ReflectHelper = ReflectHelper()

    init {
        @SuppressLint("ShowToast")
        toast = Toast.makeText(context, "", duration)
        handler = helper.getHandler(toast)
        messageQueue = helper.getMessageQueue(handler)
    }

    override fun show(pattern: String, number: Int) {
        toast.cancel()
        helper.removeMessagesFromMessageQueue(messageQueue, handler, 1, 2)
        toast.setText(String.format(pattern, number))
        toast.show()
    }
}