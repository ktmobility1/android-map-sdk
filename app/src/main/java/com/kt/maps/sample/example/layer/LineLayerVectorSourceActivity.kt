package com.kt.maps.sample.example.layer

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityLineLayerVectorBinding
import com.kt.maps.style.ColorUtils
import com.kt.maps.style.LayerFactory
import com.kt.maps.style.layers.LineLayer
import com.kt.maps.style.sources.VectorSource
import com.kt.maps.style.sources.VectorSourceProperties
import com.kt.maps.style.sources.VectorSourceProperties.Maxzoom
import com.kt.maps.style.sources.VectorSourceProperties.Minzoom
import com.kt.maps.style.sources.VectorSourceProperties.Tiles
import com.kt.maps.style.styles.LineStyleLayouts
import com.kt.maps.style.styles.LineStylePaints
import com.kt.maps.style.styles.LineStylePaints.LineColor
import com.kt.maps.style.styles.LineStylePaints.LineOpacity
import com.kt.maps.style.styles.LineStylePaints.LineWidth

class LineLayerVectorSourceActivity :
    BaseActivity<ActivityLineLayerVectorBinding>(R.layout.activity_line_layer_vector),
    OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val source: VectorSource by lazy { createSource() }
    private val layer: LineLayer by lazy { createLayer(source) }

    /**
     * 벡터 데이터 소스 초기화 및 생성
     */
    private fun createSource() = VectorSource(
        options = VectorSourceProperties(
            // 벡터 타일 URL 설정
            Tiles(arrayOf("https://tile.gis.kt.com/image/mvt_9/{z}/{x}/{y}.pbf")),
            // 벡터 데이터를 표출할 최소 지도 줌레벨 설정
            Minzoom(6),
            // 벡터 데이터를 표출할 최대 지도 줌레벨 설정
            Maxzoom(10)
        )
    )

    /**
     * 벡터 데이터 소스에 대한 레이어 초기화 및 생성
     */
    private fun createLayer(source: VectorSource) =
        LayerFactory.line(source = source.id, sourceLayer = "wd2_bound_line_1004")
            .paint(
                LineStylePaints(
                    // 색상 설정
                    LineColor("#ff0000"),
                    // 색상 투명도 설정
                    LineOpacity(1f),
                    // 색상 두께 설정
                    LineWidth(2)
                )
            )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@LineLayerVectorSourceActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            jumpTo(cameraOptions = CameraPositionOptions().zoom(9f))
            addSource(source)
            addLayer(layer)

            initOpacity()
            initWidth()
            initColor()
            initLineCap()
            initLineJoin()
        }
    }

    /**
     * 레이어 투명도 조정을 위한 slider 초기화
     */
    private fun initOpacity() {
        binding.opacitySlider.apply {
            value = layer.paint.lineOpacity.value
            addOnChangeListener { _, value, _ ->
                layer.paint(LineOpacity(value))
            }
        }
    }

    /**
     * 레이어 LineCap(선끝 처리) 변경을 위한 spinner 초기화
     */
    private fun initLineCap() {
        binding.lineCapSpinner.apply {
            adapter = ArrayAdapter(
                this@LineLayerVectorSourceActivity,
                android.R.layout.simple_spinner_item,
                LineStyleLayouts.LineCap.values()
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(layer.layout.lineCap.ordinal)

            // spinner item 선택시 animation type 변경
            onItemSelectedListener = this@LineLayerVectorSourceActivity
        }
    }

    /**
     * 레이어 LineJoin(선 연결 처리) 변경을 위한 spinner 초기화
     */
    private fun initLineJoin() {
        binding.lineJoinSpinner.apply {
            adapter = ArrayAdapter(
                this@LineLayerVectorSourceActivity,
                android.R.layout.simple_spinner_item,
                LineStyleLayouts.LineJoin.values()
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(layer.layout.lineJoin.ordinal)

            // spinner item 선택시 animation type 변경
            onItemSelectedListener = this@LineLayerVectorSourceActivity
        }
    }

    /**
     * spinner item 설정 처리를 위한 리스너
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent) {
            binding.lineCapSpinner -> layer.layout(LineStyleLayouts.LineCap.values()[position])
            binding.lineJoinSpinner -> layer.layout(LineStyleLayouts.LineJoin.values()[position])
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


    /**
     * 레이어 선형 두께 조정을 위한 slider 초기화
     */
    private fun initWidth() {
        binding.widthSlider.apply {
            value = layer.paint.lineWidth.value.toFloat()
            addOnChangeListener { _, value, _ ->
                layer.paint(LineWidth(value.toInt()))
            }
        }
    }

    /**
     * 레이어 색상 조정을 위한 slider 초기화
     */
    private fun initColor() {
        // 색상 변경
        binding.run {
            val color = Color.parseColor(layer.paint.lineColor.value)

            // RED 색상 변경 적용
            colorRSlider.apply {
                value = color.red.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
            // GREEN 색상 변경 적용
            colorGSlider.apply {
                value = color.green.toFloat()
                addOnChangeListener { _, _, _ ->
                    updateColor()
                }
            }
            // BLUE 색상 변경 적용
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
            layer.paint(LineColor(color))
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