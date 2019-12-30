package ru.yandex.money.alcexample

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
        setContentView(FrameLayout {
            Text("DI example\nCoolTool.extraInfo=\"${coolTool.extraInfo}\"")
        })
    }
}

class InjectingLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? RequireCoolTool)?.coolTool = CoolToolImpl()
    }
}
