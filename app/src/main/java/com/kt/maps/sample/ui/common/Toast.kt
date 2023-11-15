package com.kt.maps.sample.ui.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes resId: Int, vararg formatArgs: Any) {
    Toast.makeText(this, getString(resId, *formatArgs), Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message : String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}