package ru.yandex.money.aclexample

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.setPadding
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.DialogFragment

class DialogCallback(val dialogFragment: DialogFragment) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            (activity as? DialogActivity)
                ?.supportFragmentManager
                ?.also { fragmentManager ->
                    if (fragmentManager.findFragmentByTag(dialogFragment.javaClass.name) == null) {
                        dialogFragment.show(fragmentManager, dialogFragment.javaClass.name)
                    }
                }
        }
    }

}

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            AppCompatTextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER)
                text = "Dialog example"
            }.also(::addView)
        })
    }
}

class ExampleDialogFragment : AppCompatDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AppCompatTextView(context).apply {
            layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            setPadding(resources.getDimensionPixelOffset(R.dimen.offset))
            TextViewCompat.setTextAppearance(this, R.style.TextAppearance_AppCompat_Caption)
            gravity = Gravity.CENTER
            text = "Example dialog fragment"
        }
    }
}
