package ru.yoo.money.alcexample

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


interface DaggerTool {
    val extraInfo: String
}

class DaggerToolImpl : DaggerTool {
    override val extraInfo = "i am dependency"
}

class DaggerInjectingLifecycleCallbacks(
    val dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
) : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        dispatchingAndroidInjector.maybeInject(activity)
    }
}

class DaggerActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerTool: DaggerTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout {
            Text("Dagger example\nDaggerTool.extraInfo=\"${daggerTool.extraInfo}\"")
        })
    }
}

@Module
class DaggerModule {
    @Provides
    fun provideDaggerTool(): DaggerTool {
        return DaggerToolImpl()
    }
}

@Module
abstract class DaggerActivityModule {
    @ContributesAndroidInjector(modules = [DaggerModule::class])
    abstract fun contributeYourAndroidInjector(): DaggerActivity
}

@Component(modules = [AndroidInjectionModule::class, DaggerActivityModule::class])
interface ApplicationComponent {
    fun inject(application: Application)
}