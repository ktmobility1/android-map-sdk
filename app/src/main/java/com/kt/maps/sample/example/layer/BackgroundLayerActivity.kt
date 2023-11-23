package com.kt.maps.sample.example.layer

import android.graphics.Color
import android.os.Bundle
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityBackgroundLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.BackgroundLayer
import com.kt.maps.sdk.style.styles.BackgroundStylePaints.BackgroundColor
import com.kt.maps.sdk.style.styles.BackgroundStylePaints.BackgroundOpacity
import com.kt.maps.style.ColorUtils

class BackgroundLayerActivity :
    BaseActivity<ActivityBackgroundLayerBinding>(R.layout.activity_background_layer),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val layer: BackgroundLayer by lazy { createLayer() }

    private fun createLayer() = BackgroundLayer()
        .paint(
            BackgroundOpacity(0.5f),
            BackgroundColor("#ff0000")
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@BackgroundLayerActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            addLayer(layer)
        }
        initOpacity()
        initColor()
    }

    private fun initOpacity() {
        binding.opacitySlider.run {
            value = layer.paint.backgroundOpacity.value
            addOnChangeListener { _, value, _ ->
                layer.paint(BackgroundOpacity(value))
            }
        }
    }

    private fun initColor() {
        // 색상 변경
        binding.run {
            val color = Color.parseColor(layer.paint.backgroundColor.value)

            colorRSlider.apply {
                value = color.red.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
            colorGSlider.apply {
                value = color.green.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
            colorBSlider.apply {
                value = color.blue.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
        }
    }

    private fun updateColor() {
        binding.run {
            val r = colorRSlider.value.toInt()
            val g = colorGSlider.value.toInt()
            val b = colorBSlider.value.toInt()

            val color = ColorUtils.toHexColor(Color.rgb(r, g, b))
            layer.paint(BackgroundColor(color))
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