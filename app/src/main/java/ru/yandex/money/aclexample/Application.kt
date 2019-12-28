package ru.yandex.money.aclexample

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import ru.yandex.notmoney.NotAffectedThemeActivity

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ThemeCallback(R.style.AppTheme_Custom))
        registerActivityLifecycleCallbacks(DialogCallback(ExampleDialogFragment()))
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
        setContentView(LinearLayout(ContextThemeWrapper(this, R.style.AppTheme)).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL

            Button(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Theme Example"
                setOnClickListener {
                    startActivity(Intent(it.context, ThemeActivity::class.java))
                }
            }.also(::addView)

            Button(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Not affected Theme Example"
                setOnClickListener {
                    startActivity(Intent(it.context, NotAffectedThemeActivity::class.java))
                }
            }.also(::addView)

            Button(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = "Dailog Example"
                setOnClickListener {
                    startActivity(Intent(it.context, DialogActivity::class.java))
                }
            }.also(::addView)
        })
    }
}