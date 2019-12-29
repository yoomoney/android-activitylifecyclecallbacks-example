package ru.yandex.money.aclexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class StartingActivityCallback : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            (activity as? StartForResultActivity)?.supportFragmentManager?.also { fragmentManager ->
                val startingFragment = findOrCreateFragment(fragmentManager)

                startingFragment.listener = { resultCode, data ->
                    // handle response here
                    Log.d("StartForResultExample", "resultCode: $resultCode; extras: ${data?.extras?.toMap()}")
                }

                // start Activity inside StartingFragment
            }
        }
    }

    private fun findOrCreateFragment(fragmentManager: FragmentManager): StartingFragment {
        return fragmentManager.findFragmentByTag(StartingFragment::class.java.name) as StartingFragment?
            ?: StartingFragment().apply {
                fragmentManager
                    .beginTransaction()
                    .add(this, StartingFragment::class.java.name)
                    .commit()
            }
    }
}

class StartingFragment : Fragment() {

    private val requestCode = 1

    var listener: ((Int, Intent?) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            startActivityForResult(Intent(requireContext(), ResultActivity::class.java), requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode) {
            listener?.invoke(resultCode, data)
        }
    }
}

class ResultActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("result", "result")
        })
        finish()
    }
}

class StartForResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            AppCompatTextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER)
                gravity = Gravity.CENTER
                text = "Start for result example\nsee output in Logcat by \"StartForResultExample\" tag"
            }.also(::addView)
        })
    }
}

private fun Bundle.toMap(): Map<String, Any?> {
    return keySet().map { it to this.get(it) }.toMap()
}