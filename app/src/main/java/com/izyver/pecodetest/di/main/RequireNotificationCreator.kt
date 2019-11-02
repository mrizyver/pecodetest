package com.izyver.pecodetest.di.main

import com.izyver.pecodetest.NotificationCreator

interface RequireNotificationCreator{
    fun setNotificatonCreator(creator: NotificationCreator)
}