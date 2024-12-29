package com.example.remotedatabase

import android.app.Application
import com.example.remotedatabase.dependenciesinjection.AppContainer
import com.example.remotedatabase.dependenciesinjection.MahasiswaContainer

class MahasiswaApplications:Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container=MahasiswaContainer()
    }
}