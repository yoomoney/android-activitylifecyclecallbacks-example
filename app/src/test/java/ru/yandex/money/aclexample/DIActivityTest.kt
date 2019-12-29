package ru.yandex.money.aclexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class DIActivityTest {
    @Test
    fun `should access extraInfo when created`() {
        // prepare
        val mockTool: CoolTool = mock()
        val application = getApplicationContext<android.app.Application>()
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    (activity as? RequireCoolTool)?.coolTool = mockTool
                }
            })

        // invoke
        launch<DIActivity>(Intent(application, DIActivity::class.java))

        // assert
        verify(mockTool).extraInfo
    }
}