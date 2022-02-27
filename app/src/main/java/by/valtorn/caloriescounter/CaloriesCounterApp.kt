package by.valtorn.caloriescounter

import android.app.Application
import timber.log.Timber

class CaloriesCounterApp : Application() {

    companion object {
        lateinit var instance: CaloriesCounterApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}