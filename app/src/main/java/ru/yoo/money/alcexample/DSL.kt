package ru.yoo.money.alcexample

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

fun Context.LinearLayout(
    @StyleRes theme: Int = 0,
    orientation: Int = LinearLayout.VERTICAL,
    gravity: Int = Gravity.CENTER,
    children: LinearLayout.() -> Unit
): LinearLayout {
    return LinearLayout(takeIf { theme == 0 } ?: ContextThemeWrapper(this, theme)).apply {
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        this.orientation = orientation
        this.gravity = gravity

        children()
    }
}

fun Context.FrameLayout(
    children: FrameLayout.() -> Unit
): FrameLayout {
    return FrameLayout(this).apply {
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        children()
    }
}

fun ViewGroup.Button(
    @StyleRes theme: Int = 0,
    text: String,
    onClickListener: ((View) -> Unit)? = null
): AppCompatButton {
    return AppCompatButton(context.takeIf { theme == 0 } ?: ContextThemeWrapper(context, theme)).apply {
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        this.text = text
        setOnClickListener(onClickListener)
    }.also(::addView)
}

fun FrameLayout.Text(text: String): AppCompatTextView {
    return AppCompatTextView(context).apply {
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        gravity = Gravity.CENTER
        this.text = text
    }.also(::addView)
}