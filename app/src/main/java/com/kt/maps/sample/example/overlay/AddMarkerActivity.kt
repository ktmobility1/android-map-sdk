package com.kt.maps.sample.example.overlay

import android.graphics.PointF
import android.os.Bundle
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.kt.maps.geometry.LngLat
import com.kt.maps.gesture.OnMapTapListener
import com.kt.maps.gesture.addOnMapTapListener
import com.kt.maps.overlay.marker.Marker
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.overlay.marker.OnMarkerTapListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAddMarkerBinding
import com.kt.maps.sample.ui.common.showSnackbar
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback
import com.kt.maps.sdk.style.layers.SymbolLayer
import com.kt.maps.sdk.style.sources.GeojsonSource
import com.kt.maps.sdk.style.styles.SymbolIconStyleLayouts
import com.kt.maps.sdk.style.styles.SymbolStyleLayouts
import com.kt.maps.sdk.style.styles.SymbolStylePaints
import com.kt.maps.sdk.style.styles.SymbolTextStyleLayouts
import com.kt.maps.sdk.style.styles.SymbolTextStylePaints
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import java.text.DecimalFormat

class AddMarkerActivity : BaseActivity<ActivityAddMarkerBinding>(R.layout.activity_add_marker),
    OnMapReadyCallback, OnMapTapListener, OnMarkerTapListener {

    companion object {
        private const val ID_FEATURE_PROPERTY = "id"
        private const val TITLE_FEATURE_PROPERTY = "title"
    }

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private var customMarker: Marker? = null
    private var count = 0
    private var isShowableSymbolLayer = false

    private lateinit var layer: SymbolLayer

    private val source = GeojsonSource(
        features = FeatureCollection.fromFeatures(
            arrayOf(
                Feature.fromGeometry(
                    Point.fromLngLat(127.029414, 37.471401),
                    featureProperties("textField", "kt우면")
                ),
                Feature.fromGeometry(
                    Point.fromLngLat(126.978916, 37.572020),
                    featureProperties("textField", "kt광화문")
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@AddMarkerActivity)
            binding.btnMarkerCaption.setOnClickListener { addMarkerCaption() }
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            addOnMapTapListener(this@AddMarkerActivity)
            setOnMarkerTapListener(this@AddMarkerActivity)
        }
    }

    override fun onTap(point: PointF, lngLat: LngLat): Boolean {
        count++
        map.apply {
            // 마커 새로 생성할 때
            customMarker = map.addOverlay(
                MarkerOptions.Builder().apply {
                    position(lngLat)
                    title("Custom marker $count")
                    snippet(
                        "${DecimalFormat("#.#####").format(lngLat.latitude)}, ${
                            DecimalFormat("#.#####").format(
                                lngLat.longitude
                            )
                        }"
                    )
                }.build()
            )
            mapView.showSnackbar("마커를 Tap하면 마커가 삭제됩니다.")
            return true
        }
    }

    override fun onMarkerTap(marker: Marker): Boolean {
        // 마커 삭제 할 때
        map.removeOverlay(marker)
        return true
    }

    // 마커 캡션 추가 버튼 클릭 시
    private fun addMarkerCaption() {

        if (!isShowableSymbolLayer) {

            map.apply {
                addSource(source)

                getDrawable(com.kt.maps.overlay.marker.R.drawable.ktmap_marker_icon_default)?.let {
                    map.addImage("symbolDrawable", drawable = it)

                    layer = SymbolLayer(source = source.id)
                        .paint(
                            SymbolStylePaints(
                                textStylePaints = SymbolTextStylePaints(
                                    SymbolTextStylePaints.TextColor("#000000")
                                )
                            )
                        ).layout(
                            SymbolStyleLayouts(
                                iconStyleLayouts = SymbolIconStyleLayouts(
                                    SymbolIconStyleLayouts.IconImage("symbolDrawable"),
                                    SymbolIconStyleLayouts.IconSize(1.0f),
                                    SymbolIconStyleLayouts.IconAnchor.BOTTOM
                                ),
                                textStyleLayouts = SymbolTextStyleLayouts(
                                    SymbolTextStyleLayouts.TextField(fieldName = "title"),
                                    SymbolTextStyleLayouts.TextAnchor.TOP,
                                    SymbolTextStyleLayouts.TextOffset(arrayOf(0, 1))
                                )
                            )
                        )
                }
                addLayer(layer)
            }
        } else {
            map.apply {
                removeLayer(layer)
                removeSource(source)
            }
        }
        isShowableSymbolLayer = isShowableSymbolLayer.not()
    }

    private fun featureProperties(id: String, title: String) = JsonObject().apply {
        add(ID_FEATURE_PROPERTY, JsonPrimitive(id))
        add(TITLE_FEATURE_PROPERTY, JsonPrimitive(title))
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

