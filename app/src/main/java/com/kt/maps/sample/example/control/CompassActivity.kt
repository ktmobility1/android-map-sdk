package com.kt.maps.sample.example.control

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.controls.OnCompassClickListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityCompassBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class CompassActivity : BaseActivity<ActivityCompassBinding>(R.layout.activity_compass),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        //only compass enable
        map.compass.enabled = true
        map.zoomControls.enabled = false
        map.logo.enabled = false
        map.scaleBar.enabled = false
        map.currentLocation.enabled = false
        map.panControls.enabled = false

        val defaultMargin = map.compass.marginTop
        val onCompassClick = OnCompassClickListener {
            Toast.makeText(
                this@CompassActivity,
                R.string.compass_click_toast,
                Toast.LENGTH_SHORT
            ).show()
        }


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
            val margin = if (isChecked) {
                defaultMargin * 5
            } else {
                defaultMargin
            }
            map.compass.run {
                marginTop = margin
                marginBottom = margin
                marginLeft = margin
                marginRight = margin
            }
        }
        binding.compassImage.setOnCheckedChangeListener { _, isChecked ->
            val drawable = if (isChecked) {
                AppCompatResources.getDrawable(this@CompassActivity, R.drawable.btn_compass_reverse)

            } else {
                AppCompatResources.getDrawable(this@CompassActivity, R.drawable.btn_compass)
            }
            map.compass.compassIcon = drawable!!
        }
        binding.compassAlpha.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                map.compass.opacity = 0.5f
            } else {
                map.compass.opacity = 1f
            }
        }
        binding.compassToast.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                map.compass.addOnCompassClickListener(onCompassClick)
            } else {
                map.compass.removeOnCompassClickListener(onCompassClick)
            }
        }
    }
}