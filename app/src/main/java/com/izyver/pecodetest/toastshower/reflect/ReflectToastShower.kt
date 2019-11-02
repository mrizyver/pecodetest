package com.izyver.pecodetest.toastshower.reflect

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.MessageQueue
import android.widget.Toast
import com.izyver.pecodetest.toastshower.NavigationToastShower

class ReflectToastShower(
    context: Context, duration: Int,
    deletedPattern: String,
    createdPattern: String
) : NavigationToastShower(context, duration, deletedPattern, createdPattern) {

    constructor(
        context: Context, duration: Int,
        deletedPattern: Int, createdPattern: Int
    ) : this(
        context, duration,
        context.getString(deletedPattern),
        context.getString(createdPattern)
    )

    private val toast: Toast
    private val handler: Handler?
    private val messageQueue: MessageQueue?
    private val helper: ReflectHelper =ReflectHelper()

    init {
        @SuppressLint("ShowToast")
        toast = Toast.makeText(context, "", duration)
        handler = helper.getHandler(toast)
        messageQueue = helper.getMessageQueue(handler)
    }

    override fun createdScreen(number: Int) {
        toast.cancel()
        helper.removeMessagesFromMessageQueue(messageQueue, handler, 1, 2)
        toast.setText(String.format(createdPattern, number))
        toast.show()
    }

    override fun deletedScreen(number: Int) {
        toast.cancel()
        helper.removeMessagesFromMessageQueue(messageQueue, handler, 1, 2)
        toast.setText(String.format(deletedPattern, number))
        toast.show()
    }
}