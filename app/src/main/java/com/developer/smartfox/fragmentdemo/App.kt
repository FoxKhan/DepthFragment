package com.developer.smartfox.fragmentdemo

import android.app.Application
import com.developer.smartfox.fragmentdemo.di.AppComponent
import com.developer.smartfox.fragmentdemo.di.DaggerAppComponent

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        graph = DaggerAppComponent
            .builder()
            .build()
    }

    companion object {
        lateinit var graph: AppComponent
            private set
    }

    //TODO onBackInApp observable dismiss
}