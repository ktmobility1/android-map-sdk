package com.kt.maps.sample.example.events

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.gesture.OnScaleListener
import com.kt.maps.gesture.addOnMapDoubleTapListener
import com.kt.maps.gesture.addOnMapTwoFingerTapListener
import com.kt.maps.gesture.addOnScaleListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityZoomGesturesEventBinding
import com.kt.maps.sample.ui.common.GestureAlert
import com.kt.maps.sample.ui.common.GestureAlertsAdapter
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class ZoomGestureEventActivity :
    BaseActivity<ActivityZoomGesturesEventBinding>(R.layout.activity_zoom_gestures_event),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private val gestureAlertsAdapter: GestureAlertsAdapter = GestureAlertsAdapter()
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.alertsRecycler.layoutManager = LinearLayoutManager(this)
        binding.alertsRecycler.adapter = gestureAlertsAdapter
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
        map.zoomControls.enabled = false

        val layoutParams = binding.alertsRecycler.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.height = (binding.map.height / 6.80).toInt()
        layoutParams.width = binding.map.width
        binding.alertsRecycler.layoutParams = layoutParams

        attachListeners()
    }

    private fun attachListeners() {
        // Double Tap Gesture
        map.addOnMapDoubleTapListener { point, coord ->
            gestureAlertsAdapter.addAlert(
                GestureAlert(GestureAlert.TYPE_NONE, "Double Tap 이벤트가 발생하였습니다.")
            )
            true
        }

        // Two Finger Tap Gesture
        map.addOnMapTwoFingerTapListener { point, coord ->
            gestureAlertsAdapter.addAlert(
                GestureAlert(GestureAlert.TYPE_NONE, "Two Finger Tap 이벤트가 발생하였습니다.")
            )
            true
        }

        // Scale Gesture
        map.addOnScaleListener(
            object : OnScaleListener {
                override fun onScaleBegin() {
                }

                override fun onScale(): Boolean {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Scale Zoom 이벤트가 발생하였습니다.")
                    )
                    return true
                }

                override fun onScaleEnd() {
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}