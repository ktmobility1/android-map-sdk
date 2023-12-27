package com.kt.maps.sample.example.control

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.controls.OnCompassTapListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityCompassBinding
import com.kt.maps.sample.ui.common.showSnackbar

class CompassActivity : BaseActivity<ActivityCompassBinding>(R.layout.activity_compass),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@CompassActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            //only compass enable
            zoomControls.enabled = false
            logo.enabled = false
            scaleBar.enabled = false
            currentLocation.enabled = false
            panControls.enabled = false
        }

        val defaultMargin = if(binding.compassMargin.isChecked) {
            map.compass.marginTop / 5
        }else {
            map.compass.marginTop
        }

        map.compass.enabled = binding.compassEnable.isChecked
        processCompassTapListen(binding.compassTap.isChecked)


        binding.compassEnable.setOnCheckedChangeListener { _, isChecked ->
            map.compass.enabled = isChecked
        }
        binding.compassCenter.setOnCheckedChangeListener { _, isChecked ->
            map.compass.gravity = if (isChecked) {
                Gravity.CENTER
            } else {
                Gravity.TOP or Gravity.RIGHT
            }
        }
        binding.compassMargin.setOnCheckedChangeListener { _, isChecked ->
            val margin = if (isChecked)
                defaultMargin * 5
            else
                defaultMargin

            map.compass.run {
                marginTop = margin
                marginBottom = margin
                marginLeft = margin
                marginRight = margin
            }
        }
        binding.compassImage.setOnCheckedChangeListener { _, isChecked ->
            val drawable = if (isChecked)
                AppCompatResources.getDrawable(this@CompassActivity, R.drawable.btn_compass_reverse)
            else
                AppCompatResources.getDrawable(this@CompassActivity, R.drawable.btn_compass)

            map.compass.compassIcon = drawable!!
        }
        binding.compassAlpha.setOnCheckedChangeListener { _, isChecked ->
            map.compass.opacity = if (isChecked) 0.5f else 1f
        }
        binding.compassTap.setOnCheckedChangeListener { _, isChecked ->
            processCompassTapListen(isChecked)
        }
    }

    private val onCompassTap = OnCompassTapListener {
        mapView.showSnackbar(R.string.compass_tap)
    }

    private fun processCompassTapListen(add: Boolean) {
        if (add)
            map.compass.addOnCompassTapListener(onCompassTap)
        else
            map.compass.removeOnCompassTapListener(onCompassTap)
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