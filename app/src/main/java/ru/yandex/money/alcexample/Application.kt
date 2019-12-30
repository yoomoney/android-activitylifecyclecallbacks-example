package ru.yandex.money.alcexample

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.android.DispatchingAndroidInjector
import ru.yandex.notmoney.NotAffectedThemeActivity
import javax.inject.Inject

class Application : Application() {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.create().inject(this)

        registerActivityLifecycleCallbacks(ThemeCallback(R.style.AppTheme_Custom))
        registerActivityLifecycleCallbacks(DialogCallback(ExampleDialogFragment()))
        registerActivityLifecycleCallbacks(StartingActivityCallback())
        registerActivityLifecycleCallbacks(
            AnalyticsActivityCallback { name, params ->
                Log.d("Analytics", "$name $params")
            }
        )
        registerActivityLifecycleCallbacks(InjectingLifecycleCallbacks())
        registerActivityLifecycleCallbacks(DaggerInjectingLifecycleCallbacks(dispatchingAndroidInjector))
    }

    interface ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityDestroyed(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityResumed(activity: Activity) {}
    }
}

class EntryPointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LinearLayout(theme = R.style.AppTheme) {
            Button(text = "Theme Example") {
                startActivity(Intent(it.context, ThemeActivity::class.java))
            }

            Button(text = "Not affected Theme Example") {
                startActivity(Intent(it.context, NotAffectedThemeActivity::class.java))
            }

            Button(text = "Dailog Example") {
                startActivity(Intent(it.context, DialogActivity::class.java))
            }

            Button(text = "StartForResult Example") {
                startActivity(Intent(it.context, StartForResultActivity::class.java))
            }

            Button(text = "Analytics Example") {
                startActivity(Intent(it.context, AnalyticsActivity::class.java))
            }

            Button(text = "DI Example") {
                startActivity(Intent(it.context, DIActivity::class.java))
            }

            Button(text = "Dagger Example") {
                startActivity(Intent(it.context, DaggerActivity::class.java))
            }
        })
    }
}