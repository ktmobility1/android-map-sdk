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
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityCurrentLocationBinding

class CurrentLocationActivity :
    BaseActivity<ActivityCurrentLocationBinding>(R.layout.activity_current_location),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@CurrentLocationActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            //only current location enable
            compass.enabled = false
            zoomControls.enabled = false
            logo.enabled = false
            scaleBar.enabled = false
            currentLocation.enabled = binding.currentLocationEnabled.isChecked
            panControls.enabled = false

            //user location icon enable
            locationPuckEnabled = binding.currentLocationPuckEnabled.isChecked
            //user location heading enabled
            locationHeadingEnabled = binding.currentLocationHeadingEnabled.isChecked
        }

        var defaultBottomMargin = map.currentLocation.marginBottom
        var defaultLeftMargin = map.currentLocation.marginLeft
        if (binding.currentLocationMargin.isChecked) {
            defaultBottomMargin /= 2
            defaultLeftMargin /= 2
        }

        binding.currentLocationEnabled.setOnCheckedChangeListener { _, isChecked ->
            map.currentLocation.enabled = isChecked
        }
        binding.currentLocationPuckEnabled.setOnCheckedChangeListener { _, isChecked ->
            map.locationPuckEnabled = isChecked
        }
        binding.currentLocationHeadingEnabled.setOnCheckedChangeListener { _, isChecked ->
            map.locationHeadingEnabled = isChecked
        }
        binding.currentLocationRight.setOnCheckedChangeListener { _, isChecked ->
            map.currentLocation.gravity = if (isChecked) {
                Gravity.BOTTOM or Gravity.RIGHT
            } else {
                Gravity.BOTTOM or Gravity.LEFT
            }
        }
        binding.currentLocationMargin.setOnCheckedChangeListener { _, isChecked ->
            val margin = if (isChecked) {
                Pair(defaultBottomMargin * 2, defaultLeftMargin * 2)
            } else {
                Pair(defaultBottomMargin, defaultLeftMargin)
            }
            map.currentLocation.run {
                marginTop = margin.first
                marginBottom = margin.first
                marginLeft = margin.second
                marginRight = margin.second
            }
        }
        binding.currentLocationImage.setOnCheckedChangeListener { _, isChecked ->
            val drawable = if (isChecked) {
                AppCompatResources.getDrawable(
                    this@CurrentLocationActivity,
                    R.drawable.btn_current_position_reverse
                )

            } else {
                AppCompatResources.getDrawable(
                    this@CurrentLocationActivity,
                    R.drawable.btn_current_position
                )
            }
            map.currentLocation.locationIcon = drawable!!
        }
        binding.currentLocationAlpha.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                map.currentLocation.opacity = 0.5f
            } else {
                map.currentLocation.opacity = 1f
            }
        }
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