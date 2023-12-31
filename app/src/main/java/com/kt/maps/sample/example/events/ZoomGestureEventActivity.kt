package com.kt.maps.sample.example.events

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
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

class ZoomGestureEventActivity :
    BaseActivity<ActivityZoomGesturesEventBinding>(R.layout.activity_zoom_gestures_event),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val gestureAlertsAdapter: GestureAlertsAdapter = GestureAlertsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@ZoomGestureEventActivity)
        }

        binding.alertsRecycler.apply {
            layoutManager = LinearLayoutManager(this@ZoomGestureEventActivity)
            adapter = gestureAlertsAdapter
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            zoomControls.enabled = false
        }

        val layoutParams =
            (binding.alertsRecycler.layoutParams as ConstraintLayout.LayoutParams).apply {
                height = (binding.map.height / 6.80).toInt()
                width = binding.map.width
            }
        binding.alertsRecycler.layoutParams = layoutParams

        attachListeners()
    }

    private fun attachListeners() {
        // Double Tap Gesture
        map.addOnMapDoubleTapListener { point, lngLat ->
            gestureAlertsAdapter.addAlert(
                GestureAlert(GestureAlert.TYPE_NONE, "Double Tap 이벤트가 발생하였습니다.")
            )
            true
        }

        // Two Finger Tap Gesture
        map.addOnMapTwoFingerTapListener { point, lngLat ->
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}