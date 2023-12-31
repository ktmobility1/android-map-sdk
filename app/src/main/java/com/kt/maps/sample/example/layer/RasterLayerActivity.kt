package com.kt.maps.sample.example.layer

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityRasterLayerBinding
import com.kt.maps.style.LayerFactory
import com.kt.maps.style.layers.RasterLayer
import com.kt.maps.style.sources.RasterSource
import com.kt.maps.style.sources.RasterSourceProperties
import com.kt.maps.style.styles.RasterStylePaints
import com.kt.maps.style.styles.RasterStylePaints.RasterOpacity

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

    /**
     * 래스터 지도 데이터 소스 초기화 및 생성
     */
    private fun createSource() = RasterSource(
        options = RasterSourceProperties(
            // 래스터 지도 타일 URL 설정
            RasterSourceProperties.Tiles(URLS),
            // 래스터 지도 타일 크기 설정
            RasterSourceProperties.TileSize(256)
        )
    )

    /**
     * 데이터 소스를 통한 레이어 초기화 및 생성
     */
    private fun createLayer(source: RasterSource) = LayerFactory.raster(source = source.id)

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

    /**
     * 레이어 투명도 조정을 위한 slider 초기화
     */
    private fun initOpacity() {
        binding.opacitySlider.apply {
            value = layer.paint.rasterOpacity.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterOpacity(value))
            }
        }
    }

    /**
     * 레이어 밝기 조정을 위한 slider 초기화
     */
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

    /**
     * 레이어 대비 조정을 위한 slider 초기화
     */
    private fun initContrast() {
        binding.contrastSlider.apply {
            value = layer.paint.rasterContrast.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterStylePaints.RasterContrast(value))
            }
        }
    }

    /**
     * 레이어 Saturation 조정을 위한 slider 초기화
     */
    private fun initSaturation() {
        binding.saturationSlider.apply {
            value = layer.paint.rasterSaturation.value
            addOnChangeListener { _, value, _ ->
                layer.paint(RasterStylePaints.RasterSaturation(value))
            }
        }
    }

    /**
     * 레이어 hue-rotate 조정을 위한 slider 초기화
     */
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}