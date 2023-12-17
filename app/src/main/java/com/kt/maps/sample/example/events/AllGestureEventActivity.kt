package com.kt.maps.sample.example.events

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.gesture.OnFlingListener
import com.kt.maps.gesture.OnPanListener
import com.kt.maps.gesture.OnPitchListener
import com.kt.maps.gesture.OnRotateListener
import com.kt.maps.gesture.OnScaleListener
import com.kt.maps.gesture.addOnFlingListener
import com.kt.maps.gesture.addOnPanListener
import com.kt.maps.gesture.addOnPitchListener
import com.kt.maps.gesture.addOnRotateListener
import com.kt.maps.gesture.addOnScaleListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAllGesturesEventBinding
import com.kt.maps.sample.ui.common.GestureAlert
import com.kt.maps.sample.ui.common.GestureAlertsAdapter

class AllGestureEventActivity :
    BaseActivity<ActivityAllGesturesEventBinding>(R.layout.activity_all_gestures_event),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val gestureAlertsAdapter: GestureAlertsAdapter = GestureAlertsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply { }
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.alertsRecycler.layoutManager = LinearLayoutManager(this)
        binding.alertsRecycler.adapter = gestureAlertsAdapter
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        val layoutParams = binding.alertsRecycler.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.height = (binding.map.height / 6.80).toInt()
        layoutParams.width = binding.map.width
        binding.alertsRecycler.layoutParams = layoutParams

        attachListeners()
    }

    private fun attachListeners() {
        // Pan Gesture
        map.addOnPanListener(
            object : OnPanListener {
                override fun onPanBegin() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Pan 이벤트가 시작되었습니다.")
                    )
                }

                override fun onPan(): Boolean {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Pan 이벤트가 진행중입니다.")
                    )
                    return true
                }

                override fun onPanEnd() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Pan 이벤트가 종료되었습니다.")
                    )
                }
            }
        )

        // Rotate Gesture
        map.addOnRotateListener(
            object : OnRotateListener {
                override fun onRotateBegin() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Rotate 이벤트가 시작되었습니다.")
                    )
                }

                override fun onRotate(): Boolean {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Rotate 이벤트가 진행중입니다.")
                    )
                    return true
                }

                override fun onRotateEnd() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Rotate 이벤트가 종료되었습니다.")
                    )
                }
            }
        )

        // Pitch Gesture
        map.addOnPitchListener(
            object : OnPitchListener {
                override fun onPitchBegin() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Pitch 이벤트가 시작되었습니다.")
                    )
                }

                override fun onPitch(): Boolean {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Pitch 이벤트가 진행중입니다.")
                    )
                    return true
                }

                override fun onPitchEnd() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Pitch 이벤트가 종료되었습니다.")
                    )
                }
            }
        )

        // Scale Gesture
        map.addOnScaleListener(
            object : OnScaleListener {
                override fun onScaleBegin() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Scale 이벤트가 시작되었습니다.")
                    )
                }

                override fun onScale(): Boolean {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Scale 이벤트가 진행중입니다.")
                    )
                    return true
                }

                override fun onScaleEnd() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Scale 이벤트가 종료되었습니다.")
                    )
                }
            }
        )

        map.addOnFlingListener(
            object : OnFlingListener {
                override fun onFling() {
                    gestureAlertsAdapter.addAlert(
                        GestureAlert(GestureAlert.TYPE_NONE, "Fling 이벤트가 시작되었습니다.")
                    )
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