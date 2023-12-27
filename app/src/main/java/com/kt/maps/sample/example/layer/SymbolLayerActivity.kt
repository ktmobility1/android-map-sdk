package com.kt.maps.sample.example.layer

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivitySymbolLayerBinding
import com.kt.maps.style.LayerFactory
import com.kt.maps.style.layers.SymbolLayer
import com.kt.maps.style.sources.GeojsonSource
import com.kt.maps.style.styles.SymbolIconStyleLayouts
import com.kt.maps.style.styles.SymbolIconStyleLayouts.IconAllowOverlap
import com.kt.maps.style.styles.SymbolIconStyleLayouts.IconAnchor
import com.kt.maps.style.styles.SymbolIconStyleLayouts.IconIgnorePlacement
import com.kt.maps.style.styles.SymbolIconStyleLayouts.IconImage
import com.kt.maps.style.styles.SymbolIconStyleLayouts.IconRotate
import com.kt.maps.style.styles.SymbolIconStyleLayouts.IconSize
import com.kt.maps.style.styles.SymbolIconStylePaints
import com.kt.maps.style.styles.SymbolIconStylePaints.IconOpacity
import com.kt.maps.style.styles.SymbolStyleLayouts
import com.kt.maps.style.styles.SymbolStylePaints
import com.kt.maps.style.styles.SymbolTextStyleLayouts
import com.kt.maps.style.styles.SymbolTextStyleLayouts.TextAllowOverlap
import com.kt.maps.style.styles.SymbolTextStyleLayouts.TextAnchor
import com.kt.maps.style.styles.SymbolTextStyleLayouts.TextIgnorePlacement
import com.kt.maps.style.styles.SymbolTextStyleLayouts.TextRotate
import com.kt.maps.style.styles.SymbolTextStyleLayouts.TextSize
import com.kt.maps.style.styles.SymbolTextStylePaints.TextOpacity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point

class SymbolLayerActivity :
    BaseActivity<ActivitySymbolLayerBinding>(R.layout.activity_symbol_layer), OnMapReadyCallback,
    AdapterView.OnItemSelectedListener {

    companion object {
        private const val ID_FEATURE_PROPERTY = "id"
        private const val TITLE_FEATURE_PROPERTY = "title"
        private fun featureProperties(id: String, title: String) = JsonObject().apply {
            add(ID_FEATURE_PROPERTY, JsonPrimitive(id))
            add(TITLE_FEATURE_PROPERTY, JsonPrimitive(title))
        }

        private val FEATURES = arrayOf(
            Feature.fromGeometry(
                Point.fromLngLat(127.029414, 37.471401),
                featureProperties("ktRandD", "!!!KT R&D!!!")
            ),
            Feature.fromGeometry(
                Point.fromLngLat(127.030334, 37.473301),
                featureProperties("ktEast", "!!!KT EAST!!!")
            ),
            Feature.fromGeometry(
                Point.fromLngLat(127.028444, 37.471001),
                featureProperties("ktWest", "!!!KT WEST!!!")
            )
        )
    }

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private val source: GeojsonSource by lazy { createSource() }
    private val layer: SymbolLayer by lazy { createLayer(source) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@SymbolLayerActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            // 지도 초기화 위치 이동
            jumpTo(
                cameraOptions = CameraPositionOptions().zoom(16f).lngLat(
                    LngLat(longitude = 127.029414, latitude = 37.471401)
                )
            )

            AppCompatResources.getDrawable(this@SymbolLayerActivity, R.drawable.cat)?.let {
                addImage("symbolDrawable", drawable = it)
                addSource(source)
                addLayer(layer)

                initOpacity()
                initSize()
                initRotate()
                initAllowOverlap()
                initIgnorePlacement()

                initAnchor()
            }
        }
    }

    /**
     * GEOJSON 데이터 소스 초기화 및 생성
     */
    private fun createSource() = GeojsonSource(features = FeatureCollection.fromFeatures(FEATURES))

    /**
     * 데이터 소스를 통해 레이어 초기화 및 생성
     */
    private fun createLayer(source: GeojsonSource) = LayerFactory.symbol(source = source.id)
        .paint(
            // 아이콘 투명도 설정
            SymbolStylePaints(iconStylePaints = SymbolIconStylePaints(IconOpacity(0.9f)))
        )
        .layout(
            SymbolStyleLayouts(
                iconStyleLayouts = SymbolIconStyleLayouts(
                    // 아이콘 이미지 설정
                    IconImage("symbolDrawable"),
                    // 아이콘 이미지 크기 설정
                    IconSize(0.2f)
                ),
                textStyleLayouts = SymbolTextStyleLayouts(
                    // 텍스트를 표출하기 위한 필드명 지정
                    SymbolTextStyleLayouts.TextFieldName(TITLE_FEATURE_PROPERTY),
                )
            )
        )

    /**
     * 레이어 투명도 조정을 위한 slider 초기화
     */
    private fun initOpacity() {
        binding.iconOpacitySlider.apply {
            value = layer.paint.iconStylePaints.iconOpacity.value

            addOnChangeListener { slider, value, fromUser ->
                layer.paint(IconOpacity(value))
            }
        }
        binding.textOpacitySlider.apply {
            value = layer.paint.textStylePaints.textOpacity.value

            addOnChangeListener { slider, value, fromUser ->
                layer.paint(TextOpacity(value))
            }
        }
    }


    /**
     * 레이어 방향 조정을 위한 slider 초기화
     */
    private fun initRotate() {
        // 아이콘 방향 조정을 위한 slider
        binding.iconRotateSlider.apply {
            value = layer.layout.iconStyleLayouts.iconRotate.value.toFloat()

            addOnChangeListener { slider, value, fromUser ->
                layer.layout(
                    IconRotate(value.toInt())
                )
            }
        }
        // 텍스트 방향 조정을 위한 slider
        binding.textRotateSlider.apply {
            value = layer.layout.textStyleLayouts.textRotate.value.toFloat()

            addOnChangeListener { slider, value, fromUser ->
                layer.layout(
                    TextRotate(value.toInt())
                )
            }
        }
    }

    /**
     * 레이어내 심볼(아이콘,텍스트) 겹침때 항상 허용 여부 조정을 위한 switch 초기화
     */
    private fun initAllowOverlap() {
        binding.iconAllowOverlap.apply {
            isChecked = layer.layout.iconStyleLayouts.iconAllowOverlap.value

            setOnCheckedChangeListener { _, isChecked ->
                layer.layout(IconAllowOverlap(isChecked))
            }
        }
        binding.textAllowOverlap.apply {
            isChecked = layer.layout.textStyleLayouts.textAllowOverlap.value

            setOnCheckedChangeListener { _, isChecked ->
                layer.layout(TextAllowOverlap(isChecked))
            }
        }
    }

    /**
     * 레이어 다른 심볼(아이콘, 텍스트)로 겹칠 때 둘다 표시 여부 설정하기 위한 switch 초기화
     */
    private fun initIgnorePlacement() {
        binding.iconIgnorePlacement.apply {
            isChecked = layer.layout.iconStyleLayouts.iconIgnorePlacement.value

            setOnCheckedChangeListener { _, isChecked ->
                layer.layout(IconIgnorePlacement(isChecked))
            }
        }
        binding.textIgnorePlacement.apply {
            isChecked = layer.layout.textStyleLayouts.textIgnorePlacement.value

            setOnCheckedChangeListener { _, isChecked ->
                layer.layout(TextIgnorePlacement(isChecked))
            }
        }
    }

    /**
     * 레이어 아이콘 anchor type 선택하기 위한 spinner 초기화
     */
    private fun initAnchor() {
        binding.iconAnchorSpinner.apply {
            adapter = ArrayAdapter(
                this@SymbolLayerActivity,
                android.R.layout.simple_spinner_item,
                IconAnchor.values()
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(layer.layout.iconStyleLayouts.iconAnchor.ordinal)

            // spinner item 선택시 anchor type선택
            onItemSelectedListener = this@SymbolLayerActivity
        }

        binding.textAnchorSpinner.apply {
            adapter = ArrayAdapter(
                this@SymbolLayerActivity,
                android.R.layout.simple_spinner_item,
                TextAnchor.values()
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(layer.layout.textStyleLayouts.textAnchor.ordinal)

            // spinner item 선택시 anchor type선택
            onItemSelectedListener = this@SymbolLayerActivity
        }
    }

    /**
     * 레이어 심볼 크기 조정을 위한 slider 초기화
     */
    private fun initSize() {
        binding.iconSizeSlider.apply {
            value = layer.layout.iconStyleLayouts.iconSize.value

            addOnChangeListener { slider, value, fromUser ->
                layer.layout(
                    IconSize(value)
                )
            }
        }
        binding.textSizeSlider.apply {
            value = layer.layout.textStyleLayouts.textSize.value

            addOnChangeListener { slider, value, fromUser ->
                layer.layout(
                    TextSize(value)
                )
            }
        }
    }

    /**
     * spinner item 설정 처리를 위한 리스너
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent) {
            binding.iconAnchorSpinner -> layer.layout(IconAnchor.values()[position])
            binding.textAnchorSpinner -> layer.layout(TextAnchor.values()[position])
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
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