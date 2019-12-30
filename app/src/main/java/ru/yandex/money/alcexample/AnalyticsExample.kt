package ru.yandex.money.alcexample

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
        setContentView(FrameLayout {
            Text("Analytics example\nsee output in Logcat by \"Analytics\" tag")
        })
    }
}
