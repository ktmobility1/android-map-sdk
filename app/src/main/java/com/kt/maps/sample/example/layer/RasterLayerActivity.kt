package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityRasterLayerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.RasterLayer
import com.kt.maps.sdk.style.sources.RasterSource
import com.kt.maps.sdk.style.styles.RasterStylePaints
import com.kt.maps.sdk.style.styles.RasterStylePaints.RasterOpacity
import com.kt.maps.style.sources.RasterSourceProperties

class RasterLayerActivity :
    BaseActivity<ActivityRasterLayerBinding>(R.layout.activity_raster_layer),
    OnMapReadyCallback {

    companion object {
        val URLS = arrayOf(
            "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
            //"https://c.tile-cyclosm.openstreetmap.fr/cyclosm/11/1755/806.png"
        )
    }


    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val source: RasterSource by lazy { createSource() }
    private val layer: RasterLayer by lazy { createLayer(source) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@RasterLayerActivity)
        }
    }

    private fun createSource() = RasterSource(
        options = RasterSourceProperties(
            RasterSourceProperties.Tiles(URLS),
            RasterSourceProperties.TileSize(256)
        )
    )

    private fun createLayer(source: RasterSource) = RasterLayer(source = source.id)

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            addSource(source)
            addLayer(layer)
        }

        initOpacity()
        iniBrightness()
        initContrast()
        initSaturation()
        initHueRotate()
    }

    private fun initOpacity() {
        binding.opacitySlider.apply {
            value = layer.paint.rasterOpacity.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterOpacity(value))
            }
        }
    }

    private fun iniBrightness() {
        binding.brightnessMinSlider.apply {
            value = layer.paint.rasterBrightnessMin.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterStylePaints.RasterBrightnessMin(value))
            }
        }
        binding.brightnessMaxSlider.apply {
            value = layer.paint.rasterBrightnessMax.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterStylePaints.RasterBrightnessMax(value))
            }
        }
    }

    private fun initContrast() {
        binding.contrastSlider.apply {
            value = layer.paint.rasterContrast.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterStylePaints.RasterContrast(value))
            }
        }
    }

    private fun initSaturation() {
        binding.saturationSlider.apply {
            value = layer.paint.rasterSaturation.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterStylePaints.RasterSaturation(value))
            }
        }
    }

    private fun initHueRotate() {
        binding.hueRotateSlider.apply {
            value = layer.paint.rasterHueRotate.value.toFloat()
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterStylePaints.RasterHueRotate(value.toInt()))
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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}