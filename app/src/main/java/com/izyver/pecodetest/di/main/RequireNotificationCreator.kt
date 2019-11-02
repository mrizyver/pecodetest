package com.izyver.pecodetest.di.main

import com.izyver.pecodetest.notifications.NotificationCreator

interface RequireNotificationCreator{
    fun setNotificationCreator(creator: NotificationCreator)
}