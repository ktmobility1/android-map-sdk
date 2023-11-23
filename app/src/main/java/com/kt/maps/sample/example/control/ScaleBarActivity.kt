package com.kt.maps.sample.example.control

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityScaleBarBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class ScaleBarActivity : BaseActivity<ActivityScaleBarBinding>(R.layout.activity_scale_bar),
    OnMapReadyCallback {

    private lateinit var map: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            //only scaleBar enable
            compass.enabled = false
            zoomControls.enabled = false
            logo.enabled = false
            scaleBar.enabled = true
            currentLocation.enabled = false
            panControls.enabled = false
        }

        val defaultBottomMargin = map.scaleBar.marginBottom
        val defaultRightMargin = map.scaleBar.marginRight

        binding.scaleBarEnable.setOnCheckedChangeListener { _, isChecked ->
            map.scaleBar.enabled = isChecked
        }
        binding.scaleBarTop.setOnCheckedChangeListener { _, isChecked ->
            map.scaleBar.gravity = if (isChecked) {
                Gravity.TOP or Gravity.RIGHT
            } else {
                Gravity.BOTTOM or Gravity.RIGHT
            }
        }
        binding.scaleBarMargin.setOnCheckedChangeListener { _, isChecked ->
            val margin = if (isChecked) {
                Pair(defaultBottomMargin * 2, defaultRightMargin * 2)
            } else {
                Pair(defaultBottomMargin, defaultRightMargin)
            }
            map.scaleBar.run {
                marginTop = margin.first
                marginBottom = margin.first
                marginLeft = margin.second
                marginRight = margin.second
            }
        }
        binding.scaleBarTextColor.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) {
                ContextCompat.getColor(this@ScaleBarActivity, R.color.white)
            } else {
                ContextCompat.getColor(this@ScaleBarActivity, R.color.black)
            }
            map.scaleBar.setTextColor(color)

        }
        binding.scaleBarTextSize.setOnCheckedChangeListener { _, isChecked ->
            val pixel = if (isChecked) {
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    15f,
                    resources.displayMetrics
                )
            } else {
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    11f,
                    resources.displayMetrics
                )
            }
            map.scaleBar.setTextSize(pixel)
        }
        binding.scaleBarLineColor.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) {
                ContextCompat.getColor(this@ScaleBarActivity, R.color.white)
            } else {
                ContextCompat.getColor(this@ScaleBarActivity, R.color.black)
            }
            map.scaleBar.setLineColor(color)
        }
        binding.scaleBarMarginBetweenTextAndBar.setOnCheckedChangeListener { _, isChecked ->
            val pixel = if (isChecked) {
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10f,
                    resources.displayMetrics
                ).toInt()
            } else {
                0
            }
            map.scaleBar.setMarginBetweenTextAndBar(pixel)
        }
    }
}