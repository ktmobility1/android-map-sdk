package com.kt.maps.sample.example.layer

import android.graphics.Color
import android.os.Bundle
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityBackgroundLayerBinding
import com.kt.maps.style.ColorUtils
import com.kt.maps.style.LayerFactory
import com.kt.maps.style.layers.BackgroundLayer
import com.kt.maps.style.styles.BackgroundStylePaints.BackgroundColor
import com.kt.maps.style.styles.BackgroundStylePaints.BackgroundOpacity

class BackgroundLayerActivity :
    BaseActivity<ActivityBackgroundLayerBinding>(R.layout.activity_background_layer),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val layer: BackgroundLayer by lazy { createLayer() }

    /**
     * 배경 레이버 초기화 후 생성
     */
    private fun createLayer() = LayerFactory.background()
        .paint(
            // 투명도 설정
            BackgroundOpacity(0.5f),
            // 색상 설정
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

    /**
     * 배경 투명도 조정 하기 위한 slider 초기화
     */
    private fun initOpacity() {
        binding.opacitySlider.run {
            value = layer.paint.backgroundOpacity.value
            addOnChangeListener { _, value, _ ->
                layer.paint(BackgroundOpacity(value))
            }
        }
    }

    /**
     * 배경 색상 조정 하기 위한 slider 초기화
     */
    private fun initColor() {
        // 색상 변경
        binding.run {
            val color = Color.parseColor(layer.paint.backgroundColor.value)

            // RED 색상 변경 적용
            colorRSlider.apply {
                value = color.red.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
            // Green 색상 변경 적용
            colorGSlider.apply {
                value = color.green.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
            // Blue 색상 변경 적용
            colorBSlider.apply {
                value = color.blue.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
        }
    }

    /**
     * RGB slider에 적용된 값을 배경 레이어 색에 적용한다.
     */
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}