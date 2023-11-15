package com.kt.maps.sample.example.control

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.controls.OnLogoClickListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityLogoBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class LogoActivity : BaseActivity<ActivityLogoBinding>(R.layout.activity_logo), OnMapReadyCallback {

    private lateinit var map: KtMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        //only logo enable
        map.compass.enabled = false
        map.zoomControls.enabled = false
        map.logo.enabled = true
        map.scaleBar.enabled = false
        map.currentLocation.enabled = false
        map.panControls.enabled = false

        val defaultBottomMargin = map.logo.marginBottom
        val defaultRightMargin = map.logo.marginRight
        val onLogoClick = OnLogoClickListener {
            Toast.makeText(this@LogoActivity, R.string.logo_click_toast, Toast.LENGTH_SHORT).show()
        }

        binding.logoTop.setOnCheckedChangeListener { _, isChecked ->
            map.logo.gravity = if (isChecked) {
                Gravity.TOP or Gravity.RIGHT
            } else {
                Gravity.BOTTOM or Gravity.RIGHT
            }
        }

        binding.logoMargin.setOnCheckedChangeListener { _, isChecked ->
            val margin = if (isChecked) {
                Pair(defaultBottomMargin * 4, defaultRightMargin * 4)
            } else {
                Pair(defaultBottomMargin, defaultRightMargin)
            }
            map.logo.run {
                marginTop = margin.first
                marginBottom = margin.first
                marginLeft = margin.second
                marginRight = margin.second
            }
        }

        binding.logoClickToast.setOnCheckedChangeListener { _, isChecked ->
            map.logo
            if (isChecked) {
                map.logo.addOnLogoClickListener(onLogoClick)
            } else {
                map.logo.removeOnLogoClickListener(onLogoClick)
            }
        }
    }
}