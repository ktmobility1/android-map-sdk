package com.kt.maps.sample.example.control

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
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
import com.kt.maps.sample.databinding.ActivityZoomControlsBinding

class ZoomControlsActivity :
    BaseActivity<ActivityZoomControlsBinding>(R.layout.activity_zoom_controls), OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@ZoomControlsActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            //only zoomcontrols enable
            compass.enabled = false
            zoomControls.enabled = binding.zoomControlsEnable.isChecked
            logo.enabled = false
            scaleBar.enabled = false
            currentLocation.enabled = false
            panControls.enabled = false
        }

        var defaultTopMargin = map.zoomControls.marginTop
        var defaultRightMargin = map.zoomControls.marginRight
        var defaultZoomChange = map.zoomControls.zoomChangeAmount
        if (binding.zoomControlsMargin.isChecked) {
            defaultTopMargin /= 2
            defaultRightMargin /= 2
        }
        if (binding.zoomControlsChange.isChecked) {
            defaultZoomChange /= 3
        }

        binding.zoomControlsEnable.setOnCheckedChangeListener { _, isChecked ->
            map.zoomControls.enabled = isChecked
        }
        binding.zoomControlsBottom.setOnCheckedChangeListener { _, isChecked ->
            map.zoomControls.gravity = if (isChecked) {
                Gravity.BOTTOM or Gravity.END
            } else {
                Gravity.TOP or Gravity.END
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