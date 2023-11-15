package com.kt.maps.sample.ui.common

import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kt.maps.sample.R


class GestureAlertsAdapter : RecyclerView.Adapter<GestureAlertsAdapter.ViewHolder>() {
    private var isUpdating = false
    private val updateHandler = Handler()
    private val alerts: MutableList<GestureAlert> = ArrayList()

    companion object {
        private const val MAX_NUMBER_OF_ALERTS = 30
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var alertMessageTv: TextView = view.findViewById(R.id.alert_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_alert, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alert = alerts[position]
        holder.alertMessageTv.text = alert.message
        holder.alertMessageTv.setTextColor(
            ContextCompat.getColor(holder.alertMessageTv.context, alert.color)
        )
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    fun addAlert(alert: GestureAlert) {
        for (gestureAlert in alerts) {
            if (gestureAlert.alertType != GestureAlert.TYPE_PROGRESS) {
                break
            }
            if (alert.alertType == GestureAlert.TYPE_PROGRESS && gestureAlert == alert) {
                return
            }
        }
        if (itemCount >= MAX_NUMBER_OF_ALERTS) {
            alerts.removeAt(itemCount - 1)
        }
        alerts.add(0, alert)
        if (!isUpdating) {
            isUpdating = true
            updateHandler.postDelayed(updateRunnable, 250)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val updateRunnable = Runnable {
        notifyDataSetChanged()
        isUpdating = false
    }

    fun cancelUpdates() {
        updateHandler.removeCallbacksAndMessages(null)
    }
}

@SuppressLint("ResourceAsColor")
class GestureAlert(
    @field:Type @param:Type
    val alertType: Int,
    val message: String?
) {
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(TYPE_NONE, TYPE_START, TYPE_PROGRESS, TYPE_END, TYPE_OTHER)
    annotation class Type

    @ColorInt
    var color = 0
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val that = other as GestureAlert?

        if (alertType != that?.alertType) {
            return false
        }
        return if (message != null) message == that.message else that.message == null
    }

    override fun hashCode(): Int {
        var result = alertType
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }

    companion object {
        const val TYPE_NONE = 0
        const val TYPE_START = 1
        const val TYPE_END = 2
        const val TYPE_PROGRESS = 3
        const val TYPE_OTHER = 4
    }

    init {
        when (alertType) {
            TYPE_NONE -> color = android.R.color.black
            TYPE_END -> color = android.R.color.holo_red_dark
            TYPE_OTHER -> color = android.R.color.holo_purple
            TYPE_PROGRESS -> color = android.R.color.holo_orange_dark
            TYPE_START -> color = android.R.color.holo_green_dark
        }
    }

}
