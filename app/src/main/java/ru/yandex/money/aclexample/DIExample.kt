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

interface CoolTool {
    val extraInfo: String
}

class CoolToolImpl : CoolTool {
    override val extraInfo = "i am dependency"
}

interface RequireCoolTool {
    var coolTool: CoolTool
}

class DIActivity : AppCompatActivity(), RequireCoolTool {
    override lateinit var coolTool: CoolTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            AppCompatTextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER)
                gravity = Gravity.CENTER
                text = "DI example\nCoolTool.extraInfo=\"${coolTool.extraInfo}\""
            }.also(::addView)
        })
    }
}

class InjectingLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? RequireCoolTool)?.coolTool = CoolToolImpl()
    }
}
