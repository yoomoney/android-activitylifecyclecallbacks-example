package ru.yandex.money.aclexample

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

interface Screen {
    val screenName: String
}

interface ScreenWithParameters : Screen {
    val parameters: Map<String, String>
}

class AnalyticsActivityCallback(
    val sendAnalytics: (String, Map<String, String>?) -> Unit
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            (activity as? Screen)?.screenName?.let {
                sendAnalytics(it, (activity as? ScreenWithParameters)?.parameters)
            }
        }
    }
}

class AnalyticsActivity : AppCompatActivity(), ScreenWithParameters {

    override val screenName: String = "First screen"

    override val parameters: Map<String, String> = mapOf("key" to "value")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            AppCompatTextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER)
                gravity = Gravity.CENTER
                text = "Analytics example\nsee output in Logcat by \"Analytics\" tag"
            }.also(::addView)
        })
    }
}
