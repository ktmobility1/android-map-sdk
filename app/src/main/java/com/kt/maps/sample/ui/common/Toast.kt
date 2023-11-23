package com.kt.maps.sample.ui.common

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes resId: Int, vararg formatArgs: Any) {
    Toast.makeText(this, getString(resId, *formatArgs), Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * @param message
 */
fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

/**
 * @param message format resourceId
 * @param formatArgs message format arguments
 */
fun View.showSnackbar(@StringRes resId: Int, vararg formatArgs: Any) {
    Snackbar.make(this, context.getString(resId, *formatArgs), Snackbar.LENGTH_SHORT).show()
}