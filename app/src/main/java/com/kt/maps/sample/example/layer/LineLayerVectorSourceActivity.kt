package com.kt.maps.sample.example.layer

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityLineLayerVectorBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.LineLayer
import com.kt.maps.sdk.style.sources.VectorSource
import com.kt.maps.sdk.style.styles.LineStyleLayouts
import com.kt.maps.sdk.style.styles.LineStylePaints
import com.kt.maps.sdk.style.styles.LineStylePaints.LineColor
import com.kt.maps.sdk.style.styles.LineStylePaints.LineOpacity
import com.kt.maps.sdk.style.styles.LineStylePaints.LineWidth
import com.kt.maps.style.ColorUtils
import com.kt.maps.style.sources.VectorSourceProperties
import com.kt.maps.style.sources.VectorSourceProperties.Maxzoom
import com.kt.maps.style.sources.VectorSourceProperties.Minzoom
import com.kt.maps.style.sources.VectorSourceProperties.Tiles

class LineLayerVectorSourceActivity :
    BaseActivity<ActivityLineLayerVectorBinding>(R.layout.activity_line_layer_vector),
    OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val source: VectorSource by lazy { createSource() }
    private val layer: LineLayer by lazy { createLayer(source) }

    private fun createSource() = VectorSource(
        options = VectorSourceProperties(
            Tiles(arrayOf("https://tile.gis.kt.com/image/mvt_9/{z}/{x}/{y}.pbf")),
            Minzoom(6),
            Maxzoom(10)
        )
    )

    private fun createLayer(source: VectorSource) =
        LineLayer(source = source.id, sourceLayer = "wd2_bound_line_1004")
            .paint(
                LineStylePaints(
                    LineColor("#ff0000"),
                    LineOpacity(1.0f),
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
            jumpTo(cameraOptions = CameraPositionOptions().zoom(9.0))
            addSource(source)
            addLayer(layer)

            initOpacity()
            initWidth()
            initColor()
            initLineCap()
            initLineJoin()
        }
    }

    private fun initOpacity() {
        binding.opacitySlider.apply {
            value = layer.paint.lineOpacity.value
            addOnChangeListener { _, value, _ ->
                layer.paint(LineOpacity(value))
            }
        }
    }

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

//    private fun initLineTranslateAnchor() {
//        binding.lineTranslateAnchorSpinner.apply {
//            adapter = ArrayAdapter(
//                this@LineLayerVectorSourceActivity,
//                android.R.layout.simple_spinner_item,
//                LineStylePaints.LineTranslateAnchor.values()
//            ).also { adapter ->
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            }
//            setSelection(layer.paint.lineTranslateAnchor.ordinal)
//
//            // spinner item 선택시 animation type 변경
//            onItemSelectedListener = this@LineLayerVectorSourceActivity
//        }
//    }

    /**
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent) {
            binding.lineCapSpinner -> layer.layout(LineStyleLayouts.LineCap.values()[position])
            binding.lineJoinSpinner -> layer.layout(LineStyleLayouts.LineJoin.values()[position])
            //binding.lineTranslateAnchorSpinner -> layer.paint(LineStylePaints.LineTranslateAnchor.values()[position])
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


    private fun initWidth() {
        binding.widthSlider.apply {
            value = layer.paint.lineWidth.value.toFloat()
            addOnChangeListener { _, value, _ ->
                layer.paint(LineWidth(value.toInt()))
            }
        }
    }

    private fun initColor() {
        // 색상 변경
        binding.run {
            val color = Color.parseColor(layer.paint.lineColor.value)

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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}