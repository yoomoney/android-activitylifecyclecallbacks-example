package ru.yoo.money.alcexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class DaggerActivityTest {
    @Test
    fun `should access extraInfo when created`() {
        // prepare
        val mockTool: DaggerTool = mock()
        val application = ApplicationProvider.getApplicationContext<android.app.Application>()
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    (activity as? DaggerActivity)?.daggerTool = mockTool
                }
            })

        // invoke
        ActivityScenario.launch<DaggerActivity>(Intent(application, DaggerActivity::class.java))

        // assert
        verify(mockTool).extraInfo
    }
}