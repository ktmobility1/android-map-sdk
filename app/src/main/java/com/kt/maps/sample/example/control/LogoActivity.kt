package com.kt.maps.sample.example.control

import android.os.Bundle
import android.view.Gravity
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.controls.OnLogoTapListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityLogoBinding
import com.kt.maps.sample.ui.common.showSnackbar
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class LogoActivity : BaseActivity<ActivityLogoBinding>(R.layout.activity_logo), OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@LogoActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            //only logo enable
            compass.enabled = false
            zoomControls.enabled = false
            logo.enabled = true
            scaleBar.enabled = false
            currentLocation.enabled = false
            panControls.enabled = false
        }

        val defaultBottomMargin = map.logo.marginBottom
        val defaultRightMargin = map.logo.marginRight
        val onLogoTap = OnLogoTapListener {
            mapView.showSnackbar(R.string.logo_tap)
        }

        binding.logoTop.setOnCheckedChangeListener { _, isChecked ->
            map.logo.gravity = if (isChecked)
                Gravity.TOP or Gravity.RIGHT
            else
                Gravity.BOTTOM or Gravity.RIGHT

        }

        binding.logoMargin.setOnCheckedChangeListener { _, isChecked ->
            val margin = if (isChecked)
                Pair(defaultBottomMargin * 4, defaultRightMargin * 4)
            else
                Pair(defaultBottomMargin, defaultRightMargin)

            map.logo.run {
                marginTop = margin.first
                marginBottom = margin.first
                marginLeft = margin.second
                marginRight = margin.second
            }
        }

        binding.logoTap.setOnCheckedChangeListener { _, isChecked ->
            map.logo
            if (isChecked)
                map.logo.addOnLogoTapListener(onLogoTap)
            else
                map.logo.removeOnLogoTapListener(onLogoTap)

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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}