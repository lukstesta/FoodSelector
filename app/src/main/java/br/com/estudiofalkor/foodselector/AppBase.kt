package br.com.estudiofalkor.foodselector

import android.app.Activity
import android.app.Application
import android.os.Bundle
import br.com.estudiofalkor.foodselector.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.lang.ref.WeakReference

class AppBase : Application() {

    override fun onCreate() {
        super.onCreate()

        configureKoin()
        registerActivityLifecycleCallbacks(AnalyticsLifecycleCallbacks())
    }

    private fun configureKoin() {
        startKoin {
            androidContext(this@AppBase.applicationContext)
            modules(appModule)
        }
    }

    private class AnalyticsLifecycleCallbacks : ActivityLifecycleCallbacks, KoinComponent {

        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            loadKoinModules(module {
                factory(override = true) { WeakReference(activity) }
            })
        }

        override fun onActivityStarted(activity: Activity) {
            //not used
        }

        override fun onActivityResumed(activity: Activity) {
            //not used
        }

        override fun onActivityPaused(activity: Activity) {
            //not used
        }

        override fun onActivityStopped(activity: Activity) {
            //not used
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
            //not used
        }

        override fun onActivityDestroyed(activity: Activity) {
            //not used
        }
    }
}