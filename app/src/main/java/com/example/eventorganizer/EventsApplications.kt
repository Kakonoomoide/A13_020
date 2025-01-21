package com.example.eventorganizer

import android.app.Application
import com.example.eventorganizer.dependeciesinjection.AppEventsContainer
import com.example.eventorganizer.dependeciesinjection.EventContainerApp

class EventsApplications: Application() {
    lateinit var container: AppEventsContainer
    override fun onCreate() {
        super.onCreate()
        container = EventContainerApp()
    }
}