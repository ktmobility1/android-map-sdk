package com.kt.maps.sample.example.control

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityCurrentLocationBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class CurrentLocationActivity :
    BaseActivity<ActivityCurrentLocationBinding>(R.layout.activity_current_location),
    OnMapReadyCallback {

    private lateinit var map: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        //only current location enable
        map.compass.enabled = false
        map.zoomControls.enabled = false
        map.logo.enabled = false
        map.scaleBar.enabled = false
        map.currentLocation.enabled = true
        map.panControls.enabled = false

        //user location icon enable
        map.locationPuckEnabled = true
        //user location heading enabled
        map.locationHeadingEnabled = true

        val defaultBottomMargin = map.currentLocation.marginBottom
        val defaultLeftMargin = map.currentLocation.marginLeft

        binding.currentLocationEnabled.setOnCheckedChangeListener { _, isChecked ->
            map.currentLocation.enabled = isChecked
        }
        binding.currentLocationPuckEnabled.setOnCheckedChangeListener{ _, isChecked ->
            map.locationPuckEnabled = isChecked
        }
        binding.currentLocationHeadingEnabled.setOnCheckedChangeListener{ _, isChecked ->
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
}