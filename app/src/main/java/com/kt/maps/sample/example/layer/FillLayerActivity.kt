package com.kt.maps.sample.example.layer

import android.graphics.Color
import android.os.Bundle
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityFillLayerBinding
import com.kt.maps.style.ColorUtils
import com.kt.maps.style.LayerFactory
import com.kt.maps.style.layers.FillLayer
import com.kt.maps.style.sources.GeojsonSource
import com.kt.maps.style.styles.FillStylePaints.FillColor
import com.kt.maps.style.styles.FillStylePaints.FillOpacity
import com.kt.maps.style.styles.FillStylePaints.FillOutlineColor
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon

class FillLayerActivity :
    BaseActivity<ActivityFillLayerBinding>(R.layout.activity_fill_layer), OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val source: GeojsonSource by lazy { createSource() }
    private val layer: FillLayer by lazy { createLayer(source) }

    private fun createSource() = GeojsonSource(
        geometry = Polygon.fromLngLats(
            listOf(
                listOf(
                    Point.fromLngLat(127.017422, 37.49144),
                    Point.fromLngLat(127.018522, 37.49144),
                    Point.fromLngLat(127.018522, 37.49294),
                    Point.fromLngLat(127.017422, 37.49144)
                )
            )
        )
    )

    private fun createLayer(source: GeojsonSource) = LayerFactory.fill(source = source.id)
        .paint(
            FillColor("#ff0000"),
            FillOpacity(0.6f)
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@FillLayerActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 16f,
                    lngLat = LngLat(longitude = 127.017422, latitude = 37.49144)
                )
            )
            addSource(source)
            addLayer(layer)

            initOpacity()
            initColor()
            initOutlineColor()
        }
    }

    private fun initOpacity() {
        binding.opacitySlider.apply {
            value = layer.paint.fillOpacity.value
            addOnChangeListener { _, value, _ ->
                layer.paint(FillOpacity(value))
            }
        }
    }

    private fun initColor() {
        // 색상 변경
        binding.run {
            val color = Color.parseColor(layer.paint.fillColor.value)

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
            layer.paint(FillColor(color))
        }
    }

    private fun updateOutlineColor() {
        binding.run {
            val r = outlineColorRSlider.value.toInt()
            val g = outlineColorGSlider.value.toInt()
            val b = outlineColorBSlider.value.toInt()

            val color = ColorUtils.toHexColor(Color.rgb(r, g, b))
            layer.paint(FillOutlineColor(color))
        }
    }

    private fun initOutlineColor() {
        binding.run {
            val enabled = layer.paint.fillOutlineColor != null

            outlineColorEnabled.isChecked = enabled

            initOutlineColorEnabled(enabled = enabled)

            outlineColorEnabled.setOnCheckedChangeListener { _, isChecked ->
                initOutlineColorEnabled(enabled = isChecked)
            }
        }
    }

    private fun initOutlineColorEnabled(enabled: Boolean) {
        if (enabled) {
            // 색상 변경
            binding.run {
                val color: Int = layer.paint.fillOutlineColor?.let { Color.parseColor(it.value) }
                    ?: run { Color.BLACK }

                outlineColorRSlider.apply {
                    value = color.red.toFloat()
                    addOnChangeListener { _, _, _ ->
                        if (binding.outlineColorEnabled.isChecked) updateOutlineColor()
                    }
                }
                outlineColorGSlider.apply {
                    value = color.green.toFloat()
                    addOnChangeListener { _, _, _ ->
                        if (binding.outlineColorEnabled.isChecked) updateOutlineColor()
                    }
                }
                outlineColorBSlider.apply {
                    value = color.blue.toFloat()
                    addOnChangeListener { _, _, _ ->
                        if (binding.outlineColorEnabled.isChecked) updateOutlineColor()
                    }
                }
            }
        } else {
            layer.paint(FillOutlineColor(""))
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