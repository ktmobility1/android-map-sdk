package com.kt.maps.sample.example.control

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityZoomControlsBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class ZoomControlsActivity :
    BaseActivity<ActivityZoomControlsBinding>(R.layout.activity_zoom_controls), OnMapReadyCallback {

    private lateinit var map: KtMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        //only zoomcontrols enable
        map.compass.enabled = false
        map.zoomControls.enabled = true
        map.logo.enabled = false
        map.scaleBar.enabled = false
        map.currentLocation.enabled = false
        map.panControls.enabled = false

        val defaultTopMargin = map.zoomControls.marginTop
        val defaultRightMargin = map.zoomControls.marginRight
        val defaultZoomChange = map.zoomControls.zoomChangeAmount

        binding.zoomControlsEnable.setOnCheckedChangeListener { _, isChecked ->
            map.zoomControls.enabled = isChecked
        }
        binding.zoomControlsBottom.setOnCheckedChangeListener { _, isChecked ->
            map.zoomControls.gravity = if (isChecked) {
                Gravity.BOTTOM or Gravity.RIGHT
            } else {
                Gravity.TOP or Gravity.RIGHT
            }
        }
        binding.zoomControlsMargin.setOnCheckedChangeListener { _, isChecked ->
            val margin = if (isChecked) {
                Pair(defaultTopMargin * 2, defaultRightMargin * 2)
            } else {
                Pair(defaultTopMargin, defaultRightMargin)
            }
            map.zoomControls.run {
                marginTop = margin.first
                marginBottom = margin.first
                marginLeft = margin.second
                marginRight = margin.second
            }
        }
        binding.zoomControlsAlpha.setOnCheckedChangeListener { _, isChecked ->
            map.zoomControls.opacity = if (isChecked) {
                0.5f
            } else {
                1f
            }
        }
        binding.zoomControlsBackground.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) {
                ContextCompat.getColor(this@ZoomControlsActivity, R.color.black)
            } else {
                ContextCompat.getColor(this@ZoomControlsActivity, R.color.white)
            }
            map.zoomControls.setBackgroundColor(color)
        }
        binding.zoomControlsLinecolor.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) {
                ContextCompat.getColor(this@ZoomControlsActivity, R.color.white)
            } else {
                ContextCompat.getColor(this@ZoomControlsActivity, R.color.gray)
            }
            map.zoomControls.setLineColor(color)
        }
        binding.zoomControlsImage.setOnCheckedChangeListener { _, isChecked ->
            val drawable = if (isChecked) {
                Pair(
                    AppCompatResources.getDrawable(
                        this@ZoomControlsActivity,
                        R.drawable.selector_icon_btn_zoomin_reverse
                    ),
                    AppCompatResources.getDrawable(
                        this@ZoomControlsActivity,
                        R.drawable.selector_icon_btn_zoomout_reverse
                    ),
                )
            } else {
                Pair(
                    AppCompatResources.getDrawable(
                        this@ZoomControlsActivity,
                        R.drawable.selector_icon_btn_zoomin
                    ),
                    AppCompatResources.getDrawable(
                        this@ZoomControlsActivity,
                        R.drawable.selector_icon_btn_zoomout
                    ),
                )
            }
            map.zoomControls.run {
                zoomInIcon = drawable.first!!
                zoomOutIcon = drawable.second!!
            }
        }
        binding.zoomControlsChange.setOnCheckedChangeListener { _, isChecked ->
            map.zoomControls.zoomChangeAmount = if (isChecked) {
                defaultZoomChange * 3
            } else {
                defaultZoomChange
            }
        }
    }
}