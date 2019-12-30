package ru.yandex.money.alcexample

import android.app.Activity
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity

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
        setContentView(LinearLayout {
            Button(text = "Hi, i'm with custom theme")
            Button(theme = R.style.AppTheme, text = "Hi, i'm with app theme")
        })
    }
}