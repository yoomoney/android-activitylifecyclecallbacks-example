package ru.yandex.money.aclexample

import android.app.Activity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class ThemeCallback(@StyleRes val myTheme: Int) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activity
            .takeIf { it.javaClass.name.startsWith("ru.yandex.money") }
            ?.setTheme(myTheme)
    }

}

class ThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LinearLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL

            AppCompatButton(context).apply {
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                text = "Hi, i'm with custom theme"
            }.also(::addView)

            AppCompatButton(ContextThemeWrapper(context, R.style.AppTheme)).apply {
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                text = "Hi, i'm with app theme"
            }.also(::addView)
        })
    }
}